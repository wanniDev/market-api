package me.spring.market.point.contract

import com.fasterxml.jackson.databind.ObjectMapper
import me.spring.market.common.*
import org.slf4j.LoggerFactory
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.stereotype.Service
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Type
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt
import org.web3j.protocol.core.methods.response.EthTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Contract
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.io.File
import java.math.BigInteger
import kotlin.jvm.Throws

@Service
class ContractService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun deploy(abiName: String, vararg args: Any): String {
        val constructorParams = SCConstructor.toListWith(args)

        val web3j = Web3j.build(HttpService())
        val credentials = Credentials.create("0x6acf71b90460c409d7cb26e7fdac0f19bc00efcdc2ee48e9758560d0e9793ead")
        val mapper = ObjectMapper()
        val defaultResourceLoader = DefaultResourceLoader()
        val file: File = defaultResourceLoader.getResource("classpath:abi/${abiName}.json").file

        val scValue = mapper.readValue(file, Map::class.java).get("bytecode").toString()
        val ethGetTxCnt = web3j.ethGetTransactionCount(credentials.address, DefaultBlockParameterName.LATEST).send()

        if (ethGetTxCnt.hasError()) {
            log.error(ethGetTxCnt.error.message)
            return "fail"
        }

        val nonce = ethGetTxCnt.transactionCount
        val gasProvider = StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        val encodeConstructor = FunctionEncoder.encodeConstructor(constructorParams as MutableList<Type<Any>>?)
        val rawTransaction = RawTransaction.createContractTransaction(
            nonce,
            gasProvider.gasPrice,
            gasProvider.gasLimit,
            BigInteger.ZERO,
            scValue + encodeConstructor
        )
        val manager = RawTransactionManager(web3j, credentials)
        val ethSendTransaction = manager.signAndSend(rawTransaction)

        if (ethSendTransaction.hasError()) {
            log.error(ethSendTransaction.error.message)
            return "fail"
        }

        val transactionHash = ethSendTransaction.transactionHash

        val txReceiptProcessor = PollingTransactionReceiptProcessor(
            web3j,
            TX_END_CHECK_DURATION,
            TX_END_CHECK_RETRY
        )

        val transactionReceipt = txReceiptProcessor.waitForTransactionReceipt(transactionHash)
        val receipt = EthGetTransactionReceipt()
        receipt.result = transactionReceipt

        if (receipt.hasError()) {
            log.error(receipt.error.message)
            return "fail"
        }

        log.info("contractAddress : ${receipt.result.contractAddress}")

        return if (receipt.result.status == RECEIPT_OK) "success" else "fail"
    }
}
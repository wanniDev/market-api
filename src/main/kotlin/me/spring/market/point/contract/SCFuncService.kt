package me.spring.market.point.contract

import me.spring.market.common.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.utils.RevertReasonExtractor
import java.math.BigInteger

@Service
class SCFuncService {
    @Value("\${ethereum.erc20.contract.address}")
    private val erc20ContractAddr: String? = null

    fun mint(privateKey: String, address: String): String {
        val credentials = Credentials.create(privateKey)
        SCValidator.validateCredentials(credentials, address)

        val web3j = Web3j.build(HttpService())
        val params = ArrayList<Type<*>>()
        params.add(Address(credentials.address))
        params.add(Uint256(100L))
        val receipt = _mint(web3j, credentials, params)
        return if (receipt != null && receipt.status == RECEIPT_OK) {
            "success"
        } else {
            "fail"
        }
    }

    private fun _mint(web3j: Web3j, credentials: Credentials, params: ArrayList<Type<*>>): TransactionReceipt? {
        val output = listOf<TypeReference<*>>(TypeReference.create(Uint256::class.java))
        val input = params.toList()

        val function = Function(
            "_mint",
            input,
            output
        )

        val txReceiptProcessor = PollingTransactionReceiptProcessor(
            web3j,
            TX_END_CHECK_DURATION,
            TX_END_CHECK_RETRY
        )
        val txData = FunctionEncoder.encode(function)

        val txManager = CustomTransactionManager(web3j, credentials, CHAIN_ID, txReceiptProcessor)
        val gasProvider = StaticGasProvider(GAS_PRICE, GAS_LIMIT)
        val receipt = txManager.sendTransactionSync(
            gasProvider.gasPrice,
            gasProvider.gasLimit,
            erc20ContractAddr!!,
            txData,
            BigInteger.ZERO
        )

        if (receipt != null) {
            if (receipt.status == RECEIPT_OK) {
                print("hello")
            } else {
                receipt.revertReason = RevertReasonExtractor.extractRevertReason(receipt, txData, web3j, true, null)
            }
        }
        return receipt
    }
}
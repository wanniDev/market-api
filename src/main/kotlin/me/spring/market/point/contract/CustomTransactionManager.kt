package me.spring.market.point.contract

import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.math.BigInteger

class CustomTransactionManager(web3j: Web3j, credentials: Credentials, chinId: Long, txReciptProcessor: PollingTransactionReceiptProcessor)
    : RawTransactionManager(web3j, credentials, chinId, txReciptProcessor) {
        fun sendTransactionSync(gasPrice:BigInteger, gasLimit: BigInteger, to: String, data: String, value:BigInteger): TransactionReceipt? {
            return executeTransaction(gasPrice, gasLimit, to, data, value)
        }
}
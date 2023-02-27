package me.spring.market.point.contract

import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import java.math.BigInteger

object SCConstructor {
    fun toListWith(args: Array<out Any>): ArrayList<Type<*>> {
        val params = ArrayList<Type<*>>()

        for (arg in args.indices) {
            val data = when (args[arg].javaClass.name) {
                "java.lang.String" -> {
                    Utf8String(args[arg].toString())
                }
                "java.lang.Integer" -> {
                    Uint8(BigInteger(args[arg].toString()))
                }
                "java.lang.Long" -> {
                    Uint256(BigInteger(args[arg].toString()))
                }
                else -> {null}
            }

            if (data != null) {
                params.add(data)
            }
        }
        return params
    }
}
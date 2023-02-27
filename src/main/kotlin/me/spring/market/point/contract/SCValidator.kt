package me.spring.market.point.contract

import me.spring.market.common.exception.InvalidCredentialsException
import org.web3j.crypto.Credentials

object SCValidator {
    fun validateCredentials(credentials: Credentials, address: String) {
        val lowercase = address.lowercase()
        val address1 = credentials.address
        if (address1 != lowercase)
            throw InvalidCredentialsException()
    }
}
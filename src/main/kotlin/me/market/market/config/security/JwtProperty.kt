package me.market.market.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtProperty {
    @Value("\${jwt.token.header}")
    lateinit var header: String

    @Value("\${jwt.token.issuer}")
    lateinit var issuer: String

    @Value("\${jwt.token.clientSecret}")
    lateinit var clientSecret: String

    @Value("\${jwt.token.expirySeconds}")
    var expirySeconds: Long = 0

    @Value("\${jwt.token.refreshSeconds}")
    var refreshSeconds: Long = 0

    override fun toString(): String {
        return "JwtProperty(header='$header', issuer='$issuer', clientSecret='$clientSecret', expirySeconds=$expirySeconds, refreshSeconds=$refreshSeconds)"
    }
}
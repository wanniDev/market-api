package me.market.market.config.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

class JWTHelper(private val issuer: String, private val clientSecret: String, private val expirySeconds: Long, private val refreshSeconds: Long) {

    private val algorithm: Algorithm = Algorithm.HMAC512(clientSecret)
    private val jwtVerifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build()

    fun newToken(claims: Claims): String {
        val now = Date()
        val builder = JWT.create()
        builder.withIssuer(issuer)
        builder.withIssuedAt(now)
        if (expirySeconds > 0) {
            builder.withExpiresAt(Date(now.time + expirySeconds * 1000L))
        }
        builder.withClaim("userKey", claims.userKey)
        builder.withArrayClaim("roles", claims.roles)
        builder.withClaim("host", claims.host)
        return builder.sign(algorithm)
    }

    @Throws(JWTVerificationException::class)
    fun verify(token: String): Claims {
        return Claims(jwtVerifier.verify(token))
    }

    class Claims {
        var userKey: Long ?= null
        var roles: Array<out String?> ?= null
        var host: String ?= null
        var iat: Date ?= null
        var exp: Date ?= null

        private constructor()

        constructor(decodedJWT: DecodedJWT) {
            val userKey = decodedJWT.getClaim("userKey")
            if (!userKey.isNull) this.userKey = userKey.asLong()
            val roles = decodedJWT.getClaim("roles")
            if (!roles.isNull) this.roles = roles.asArray(String::class.java)
            val host = decodedJWT.getClaim("host")
            if (!host.isNull) this.host = host.asString()
            iat = decodedJWT.issuedAt
            exp = decodedJWT.expiresAt
        }

        fun iat(): Long {
            return if (iat != null) iat!!.time else -1
        }

        fun exp(): Long {
            return if (exp != null) exp!!.time else -1
        }

        fun eraseIat() {
            iat = null
        }

        fun eraseExp() {
            exp = null
        }

        companion object {
            fun of(userKey: Long?, roles: Array<out String?>, host: String?): Claims {
                val claims = Claims()
                claims.userKey = userKey
                claims.roles = roles
                claims.host = host
                return claims
            }
        }
    }
}
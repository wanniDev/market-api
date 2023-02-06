package me.market.market.member.domain

import jakarta.persistence.*
import jakarta.servlet.http.HttpServletRequest
import me.market.market.common.ClientIpFactory
import me.market.market.config.security.JWTHelper

@Entity
class Member(
    @Embedded
    val accountInfo: AccountInfo,
    @Embedded
    val phoneInfo: PhoneInfo,
    @Column(nullable = false)
    val passwd: String
) {
    fun newApiToken(jwtHelper: JWTHelper, roles: Array<String>, request: HttpServletRequest): String {
        return jwtHelper.newToken(
            JWTHelper.Claims.of(id, roles, ClientIpFactory.getRequestIP(request)))
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
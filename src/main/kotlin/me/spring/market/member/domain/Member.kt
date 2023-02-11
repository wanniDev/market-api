package me.spring.market.member.domain

import jakarta.persistence.*
import jakarta.servlet.http.HttpServletRequest
import me.spring.market.config.security.JWTHelper

@Entity
class Member(
    @Embedded
    val accountInfo: AccountInfo,
    @Embedded
    val phoneInfo: PhoneInfo,
    @Column(nullable = false)
    var passwd: String
) {
    fun newApiToken(jwtHelper: JWTHelper, roles: Array<String>, request: HttpServletRequest): String {
        return jwtHelper.newToken(
            JWTHelper.Claims.of(id, roles, request))
    }

    override fun toString(): String {
        return "Member(accountInfo=$accountInfo, phoneInfo=$phoneInfo, passwd='$passwd', id=$id)"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


}
package me.market.market.config.security

import me.market.market.member.domain.Member
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken: AbstractAuthenticationToken {
    private var principal: Any ?= null
    private var credentials: String ?= null

    constructor(principal: Member, credentials: String) : super(null) {
        super.setAuthenticated(false)
        this.principal = principal
        this.credentials = credentials
    }

    constructor(principal: Any, credentials: String?, authorities: Collection<GrantedAuthority?>?) : super(
        authorities
    ) {
        super.setAuthenticated(true)
        this.principal = principal
        this.credentials = credentials
    }

    override fun getPrincipal(): Any? {
        return principal
    }

    override fun getCredentials(): String? {
        return credentials
    }
}
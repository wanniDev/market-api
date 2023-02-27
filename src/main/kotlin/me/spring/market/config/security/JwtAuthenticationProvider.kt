package me.spring.market.config.security

import me.spring.market.member.domain.Member
import me.spring.market.common.exception.MemberNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.util.ClassUtils
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class JwtAuthenticationProvider: AuthenticationProvider {

    @Autowired
    private lateinit var jwtHelper: JWTHelper

    override fun authenticate(authentication: Authentication?): Authentication {
        try {
            val authenticationToken = authentication as JwtAuthenticationToken
            val member = authenticationToken.principal as Member
            val authenticated = JwtAuthenticationToken(member.id!!, null,
                AuthorityUtils.createAuthorityList("ROLE_USER")
            )
            val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
            val token: String = member.newApiToken(jwtHelper, arrayOf("ROLE_USER"), request)
            authenticated.details = token
            return authenticated
        } catch (e: MemberNotFoundException) {
            throw UsernameNotFoundException(e.message)
        } catch (e: IllegalArgumentException) {
            throw BadCredentialsException(e.message)
        } catch (e: DataAccessException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return ClassUtils.isAssignable(JwtAuthenticationToken::class.java, authentication!!)
    }
}
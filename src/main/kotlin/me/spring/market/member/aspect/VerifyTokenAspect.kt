package me.spring.market.member.aspect

import me.spring.market.config.security.JWTHelper
import me.spring.market.config.security.JwtAuthenticationToken
import me.spring.market.config.security.JwtProperty
import me.spring.market.member.exception.EmptyTokenException
import me.spring.market.member.exception.InvalidTokenException
import me.spring.market.member.infra.MemberTokenPayload
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.Date

@Aspect
@Component
class VerifyTokenAspect(private val jwtProperty: JwtProperty, private val jwtHelper: JWTHelper) {

    @Before("@annotation(me.spring.market.annotaion.RequireToken)")
    @Throws(Throwable::class)
    fun validateCaptcha(joinPoint: JoinPoint) {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader(jwtProperty.header)
        if (token.isNullOrEmpty())
            throw EmptyTokenException()
        val payload = token.split(" ")[1]

        val claims = jwtHelper.verify(payload)

        val userKey = checkNotNull(claims.userKey)
        val roles = checkNotNull(claims.roles)
        val host = checkNotNull(claims.host)

        val exp = claims.exp
        val now = Date()
        if (now > exp)
            throw InvalidTokenException()

        val grantedAuthorities = roles.asSequence()
            .map {
                SimpleGrantedAuthority(it)
            }.toList()

        val tokenPayload = MemberTokenPayload(userKey, host)

        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication: Authentication = JwtAuthenticationToken(tokenPayload, null, grantedAuthorities)
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }
}
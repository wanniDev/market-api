package me.market.market.member.service

import jakarta.servlet.http.HttpServletRequest
import me.market.market.config.security.JwtAuthenticationToken
import me.market.market.member.domain.Member
import me.market.market.member.domain.PhoneInfo
import me.market.market.member.domain.repository.MemberRepository
import me.market.market.member.exception.MemberNotFoundException
import me.market.market.member.infra.MemberTokenPayload
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = false)
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    fun registerNew(member: Member): Boolean {
        return  Objects.nonNull(memberRepository.save(member))
    }

    fun login(userId: String, passwd: String): String {
        val member = memberRepository.findByPhoneInfoPhoneNumAndPasswd(phoneNum = userId, passwd = passwd)
        if (Objects.nonNull(member)) {
            val authenticate = authenticationManager.authenticate(JwtAuthenticationToken(member, ""))
            return authenticate.details as String
        }
        throw MemberNotFoundException()
    }

    fun findMemberInfoFrom(payload: MemberTokenPayload): Member {
        return memberRepository.findMemberById(payload.userKey)
    }
}
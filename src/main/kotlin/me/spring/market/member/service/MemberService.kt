package me.spring.market.member.service

import me.spring.market.config.security.JwtAuthenticationToken
import me.spring.market.member.domain.Member
import me.spring.market.member.domain.repository.MemberRepository
import me.spring.market.member.exception.DuplicatedMemberException
import me.spring.market.member.exception.MemberNotFoundException
import me.spring.market.member.infra.MemberTokenPayload
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = false)
class MemberService(
    private val memberRepository: MemberRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwdEncoder: PasswordEncoder
) {

    @Transactional
    fun registerNew(member: Member): Boolean {
        checkDuplicate(member)
        member.passwd = passwdEncoder.encode(member.passwd)
        return  Objects.nonNull(memberRepository.save(member))
    }

    fun login(userId: String, passwd: String): String {
        val member = memberRepository.findByPhoneInfoPhoneNum (phoneNum = userId)
        val isPasswdMatches = passwdEncoder.matches(passwd, member.passwd)
        if (!Objects.nonNull(member) || !isPasswdMatches) {
            throw MemberNotFoundException()
        }
        val authenticate = authenticationManager.authenticate(JwtAuthenticationToken(member, ""))
        return authenticate.details as String
    }

    fun findMemberInfoFrom(payload: MemberTokenPayload): Member {
        return memberRepository.findMemberById(payload.userKey)
    }


    private fun checkDuplicate(member: Member) {
        if (memberRepository.existsByPhoneInfoPhoneNum(member.passwd))
            throw DuplicatedMemberException()
    }
}
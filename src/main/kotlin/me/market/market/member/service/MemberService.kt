package me.market.market.member.service

import me.market.market.member.domain.Member
import me.market.market.member.domain.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects

@Service
@Transactional(readOnly = false)
class MemberService(private val memberRepository: MemberRepository) {

    @Transactional
    fun registerNew(member: Member): Boolean {
        return  Objects.nonNull(memberRepository.save(member))
    }
}
package me.market.market.member.domain.repository

import me.market.market.member.domain.Member
import me.market.market.member.domain.PhoneInfo
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<Member, Long> {
    fun save(member: Member): Member

    fun findByPhoneInfoPhoneNum(phoneNum: String): Member

    fun findByPhoneInfoPhoneNumAndPasswd(phoneNum: String, passwd: String): Member

    fun findMemberById(id: Long): Member
}
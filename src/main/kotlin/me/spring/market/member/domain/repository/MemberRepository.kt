package me.spring.market.member.domain.repository

import me.spring.market.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository


interface MemberRepository: JpaRepository<Member, Long> {
    fun save(member: Member): Member

    fun findByPhoneInfoPhoneNum(phoneNum: String): Member

    fun findByPhoneInfoPhoneNumAndPasswd(phoneNum: String, passwd: String): Member

    fun findMemberById(id: Long): Member
}
package me.spring.market.common

import me.spring.market.member.api.request.MemberRegisterReq
import me.spring.market.member.domain.AccountInfo
import me.spring.market.member.domain.Member
import me.spring.market.member.domain.PhoneInfo

class SimpleEntityFactory {
    companion object {
        fun createMemberFrom(memberRegisterReq: MemberRegisterReq): Member {
            return Member(
                AccountInfo(memberRegisterReq.accountBank, memberRegisterReq.accountNum),
                PhoneInfo(memberRegisterReq.mobileCarrier, memberRegisterReq.phoneNum),
                memberRegisterReq.passwd
            )
        }
    }
}
package me.market.market.common

import me.market.market.member.api.request.MemberRegisterReq
import me.market.market.member.domain.AccountInfo
import me.market.market.member.domain.Member
import me.market.market.member.domain.PhoneInfo

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
package me.spring.market.common

import me.spring.market.member.api.response.MemberInfoResp
import me.spring.market.member.domain.Member


class SimpleDtoFactory {
    companion object {
        fun createMemberDtoFrom(member: Member): MemberInfoResp {
            return MemberInfoResp(
                member.phoneInfo.phoneNum
            )
        }
    }
}
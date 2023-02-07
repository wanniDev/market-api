package me.market.market.common

import me.market.market.member.api.response.MemberInfoResp
import me.market.market.member.domain.Member


class SimpleDtoFactory {
    companion object {
        fun createMemberDtoFrom(member: Member): MemberInfoResp {
            return MemberInfoResp(
                member.phoneInfo.phoneNum
            )
        }
    }
}
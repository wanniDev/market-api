package me.spring.market.member.api.request

data class MemberLoginReq(
    val userId: String,
    val passwd: String
)

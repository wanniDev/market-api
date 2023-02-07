package me.market.market.member.infra

data class MemberTokenPayload(
    val userKey: Long,
    val host: String
)
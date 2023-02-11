package me.spring.market.member.infra

data class MemberTokenPayload(
    val userKey: Long,
    val host: String
)
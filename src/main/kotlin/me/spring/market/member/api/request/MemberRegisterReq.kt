package me.spring.market.member.api.request

data class MemberRegisterReq(
    val accountBank: String,
    val accountNum: String,
    val mobileCarrier: String,
    val phoneNum: String,
    val passwd: String
)
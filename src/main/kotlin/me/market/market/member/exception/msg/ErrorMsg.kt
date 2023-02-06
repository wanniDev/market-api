package me.market.market.member.exception.msg

enum class ErrorMsg(
    val msg: String,
    val code: Int
) {
    // member
    MEMBER_NOT_FOUND(msg = "해당 회원을 찾을 수 없습니다.", 404)
}
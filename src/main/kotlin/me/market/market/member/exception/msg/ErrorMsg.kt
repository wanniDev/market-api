package me.market.market.member.exception.msg

enum class ErrorMsg(
    val msg: String,
    val code: Int
) {
    // member
    MEMBER_NOT_FOUND(msg = "해당 회원을 찾을 수 없습니다.", code = 404),

    // token
    TOKEN_NOT_FOUND(msg = "토큰을 찾을 수 없습니다.", code = 404),
    INVALID_TOKEN(msg = "올바르지 않은 토큰입니다.", code = 401)
}
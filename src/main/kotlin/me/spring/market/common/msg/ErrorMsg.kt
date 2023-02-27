package me.spring.market.common.msg

enum class ErrorMsg(
    val msg: String,
    val code: Int
) {
    // member
    MEMBER_NOT_FOUND(msg = "해당 회원을 찾을 수 없습니다.", code = 404),
    MEMBER_ALREADY_EXIST(msg = "이미 존재하는 회원 입니다.", code = 400),

    // token
    TOKEN_NOT_FOUND(msg = "토큰을 찾을 수 없습니다.", code = 404),
    INVALID_TOKEN(msg = "올바르지 않은 토큰입니다.", code = 401),

    // wallet
    INVALID_CREDENTIALS(msg = "부적절한 토큰입니다.", code = 400)
}
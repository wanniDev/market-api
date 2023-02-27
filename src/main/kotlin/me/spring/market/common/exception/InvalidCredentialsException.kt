package me.spring.market.common.exception

import me.spring.market.common.msg.ErrorMsg

class InvalidCredentialsException: RuntimeException {
    constructor(): super(ErrorMsg.INVALID_CREDENTIALS.msg)
    constructor(msg: String): super(msg)
}
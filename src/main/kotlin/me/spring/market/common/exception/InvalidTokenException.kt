package me.spring.market.common.exception

import me.spring.market.common.msg.ErrorMsg

class InvalidTokenException: RuntimeException {
    constructor(): super(ErrorMsg.INVALID_TOKEN.msg)
    constructor(msg: String): super(msg)
}
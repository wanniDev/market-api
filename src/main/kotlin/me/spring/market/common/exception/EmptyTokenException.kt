package me.spring.market.common.exception

import me.spring.market.common.msg.ErrorMsg

class EmptyTokenException: RuntimeException {
    constructor(): super(ErrorMsg.TOKEN_NOT_FOUND.msg)
    constructor(msg: String): super(msg)
}
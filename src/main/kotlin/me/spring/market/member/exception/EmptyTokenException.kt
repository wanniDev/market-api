package me.spring.market.member.exception

import me.spring.market.member.exception.msg.ErrorMsg

class EmptyTokenException: RuntimeException {
    constructor(): super(ErrorMsg.TOKEN_NOT_FOUND.msg)
    constructor(msg: String): super(msg)
}
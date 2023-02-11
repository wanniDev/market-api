package me.spring.market.member.exception

import me.spring.market.member.exception.msg.ErrorMsg

class InvalidTokenException: RuntimeException {
    constructor(): super(ErrorMsg.INVALID_TOKEN.msg)
    constructor(msg: String): super(msg)
}
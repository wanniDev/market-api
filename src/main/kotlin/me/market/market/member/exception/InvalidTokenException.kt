package me.market.market.member.exception

import me.market.market.member.exception.msg.ErrorMsg

class InvalidTokenException: RuntimeException {
    constructor(): super(ErrorMsg.INVALID_TOKEN.msg)
    constructor(msg: String): super(msg)
}
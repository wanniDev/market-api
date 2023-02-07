package me.market.market.member.exception

import me.market.market.member.exception.msg.ErrorMsg

class EmptyTokenException: RuntimeException {
    constructor(): super(ErrorMsg.TOKEN_NOT_FOUND.msg)
    constructor(msg: String): super(msg)
}
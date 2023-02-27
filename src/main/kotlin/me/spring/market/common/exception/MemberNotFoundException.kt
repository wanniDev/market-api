package me.spring.market.common.exception

import me.spring.market.common.msg.ErrorMsg

class MemberNotFoundException: RuntimeException {
    constructor(): super(ErrorMsg.MEMBER_NOT_FOUND.msg)
    constructor(msg: String): super(msg)
}
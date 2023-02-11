package me.spring.market.member.exception

import me.spring.market.member.exception.msg.ErrorMsg

class MemberNotFoundException: RuntimeException {
    constructor(): super(ErrorMsg.MEMBER_NOT_FOUND.msg)
    constructor(msg: String): super(msg)
}
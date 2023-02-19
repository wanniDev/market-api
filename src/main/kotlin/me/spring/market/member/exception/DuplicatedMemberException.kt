package me.spring.market.member.exception

import me.spring.market.member.exception.msg.ErrorMsg

class DuplicatedMemberException: RuntimeException {
    constructor(): super(ErrorMsg.MEMBER_ALREADY_EXIST.msg)
    constructor(msg: String): super(msg)
}
package me.spring.market.common.exception

import me.spring.market.common.msg.ErrorMsg

class DuplicatedMemberException: RuntimeException {
    constructor(): super(ErrorMsg.MEMBER_ALREADY_EXIST.msg)
    constructor(msg: String): super(msg)
}
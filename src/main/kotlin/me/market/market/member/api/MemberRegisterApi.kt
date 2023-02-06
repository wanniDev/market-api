package me.market.market.member.api

import me.market.market.common.SimpleEntityFactory
import me.market.market.common.API_URI_PREFIX
import me.market.market.member.api.request.MemberRegisterReq
import me.market.market.member.api.response.MemberRegisterResp
import me.market.market.member.service.MemberService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_URI_PREFIX)
class MemberRegisterApi(private val memberService: MemberService) {

    @PostMapping(value = ["member"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerMember(@RequestBody memberRegisterReq: MemberRegisterReq): ResponseEntity<MemberRegisterResp> {
        val result = memberService.registerNew(member = SimpleEntityFactory.createMemberFrom(memberRegisterReq))
        return ResponseEntity.ok(MemberRegisterResp(result))
    }
}
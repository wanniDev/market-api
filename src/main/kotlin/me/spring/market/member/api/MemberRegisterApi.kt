package me.spring.market.member.api

import me.spring.market.common.SimpleEntityFactory
import me.spring.market.common.API_URI_PREFIX
import me.spring.market.member.api.request.MemberRegisterReq
import me.spring.market.member.api.response.MemberRegisterResp
import me.spring.market.member.service.MemberService
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
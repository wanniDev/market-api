package me.spring.market.member.api

import me.spring.market.common.API_URI_PREFIX
import me.spring.market.member.api.request.MemberLoginReq
import me.spring.market.member.api.response.MemberLoginResp
import me.spring.market.member.service.MemberService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_URI_PREFIX)
class MemberAuthenticationApi(private val memberService: MemberService) {

    @PostMapping(value = ["member/login"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody memberLoginReq: MemberLoginReq): ResponseEntity<MemberLoginResp> {
        val result = memberService.login(memberLoginReq.userId, memberLoginReq.passwd)
        return ResponseEntity.ok(MemberLoginResp(result))
    }
}
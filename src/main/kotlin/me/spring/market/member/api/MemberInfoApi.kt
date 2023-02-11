package me.spring.market.member.api

import me.spring.market.annotaion.RequireToken
import me.spring.market.common.SimpleDtoFactory
import me.spring.market.common.API_URI_PREFIX
import me.spring.market.member.api.response.MemberInfoResp
import me.spring.market.member.infra.MemberTokenPayload
import me.spring.market.member.service.MemberService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(API_URI_PREFIX)
class MemberInfoApi(private val memberService: MemberService) {

    @RequireToken
    @GetMapping(value = ["member"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getMemberInfo(): ResponseEntity<MemberInfoResp> {
        val payload: MemberTokenPayload = SecurityContextHolder.getContext().authentication.principal as MemberTokenPayload
        val result = SimpleDtoFactory.createMemberDtoFrom(memberService.findMemberInfoFrom(payload))
        return ResponseEntity.ok(result)
    }
}
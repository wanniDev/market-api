package me.spring.market.member.api

import me.spring.market.AbstractTest
import me.spring.market.common.SimpleEntityFactory
import me.spring.market.common.API_URI_PREFIX
import me.spring.market.member.api.request.MemberLoginReq
import me.spring.market.member.api.request.MemberRegisterReq
import me.spring.market.member.domain.repository.MemberRepository
import me.spring.market.member.service.MemberService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class MemberLoginApiTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService
) : AbstractTest() {


    @BeforeEach
    fun init() {
        memberService.registerNew(
            SimpleEntityFactory.createMemberFrom(
            MemberRegisterReq(
                "신한은행", "111-111-111111",
                "LGU+", "010-1234-5678",
                "aaaabbbbaa")
        ))
    }

    @AfterEach
    fun clear() {
        memberRepository.deleteAll()
    }

    @Test
    @DisplayName("로그인 동작 테스트")
    fun loginTest() {
        val loginReq = MemberLoginReq(userId = "010-1234-5678", passwd = "aaaabbbbaa")
        val reqBody = objectMapper.writeValueAsString(loginReq)

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("$API_URI_PREFIX/member/login")
                .content(reqBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        result.andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.STRING)
                            .description("로그인 아이디"),
                        PayloadDocumentation.fieldWithPath("passwd").type(JsonFieldType.STRING)
                            .description("로그인 패스워드")
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("token").type(JsonFieldType.STRING)
                            .description("인증 토큰")
                    )
                )
            )
    }
}
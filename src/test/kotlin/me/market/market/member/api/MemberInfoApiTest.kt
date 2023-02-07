package me.market.market.member.api

import me.market.market.AbstractTest
import me.market.market.common.API_URI_PREFIX
import me.market.market.common.SimpleEntityFactory
import me.market.market.config.security.JWTHelper
import me.market.market.config.security.JwtProperty
import me.market.market.member.api.request.MemberLoginReq
import me.market.market.member.api.request.MemberRegisterReq
import me.market.market.member.domain.repository.MemberRepository
import me.market.market.member.infra.MemberTokenPayload
import me.market.market.member.service.MemberService
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

class MemberInfoApiTest @Autowired constructor(
    private val jwtHelper: JWTHelper,
    private val jwtProperty: JwtProperty,
    private val memberService: MemberService,
    private val memberRepository: MemberRepository): AbstractTest() {

    private lateinit var token: String

    @BeforeEach
    fun init() {
        val newMember = memberRepository.save(
            SimpleEntityFactory.createMemberFrom(
                MemberRegisterReq(
                    "신한은행", "111-111-111111",
                    "LGU+", "010-1234-5678",
                    "aaaabbbbaa"
                )
            )
        )

        token = jwtHelper.newToken(JWTHelper.Claims.of(userKey = newMember.id, roles = arrayOf("ROLE_USER"), null))
    }

    @AfterEach
    fun clear() {
        memberRepository.deleteAll()
    }

    @Test
    @DisplayName("내 정보 조회 테스트")
    fun memberInfoTest() {
        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("$API_URI_PREFIX/member")
                .header(jwtProperty.header, "Bearer $token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )

        result.andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("phoneNum").type(JsonFieldType.STRING)
                            .description("회원 핸드폰 번호")
                    )
                )
            )
    }
}
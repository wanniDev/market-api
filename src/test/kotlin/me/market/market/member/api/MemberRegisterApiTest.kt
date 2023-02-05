package me.market.market.member.api

import me.market.market.AbstractTest
import me.market.market.common.API_URI_PREFIX
import me.market.market.member.api.request.MemberRegisterReq
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class MemberRegisterApiTest: AbstractTest() {
    @Test
    @DisplayName("회원가입 동작 테스트")
    fun currentJvmMilliseconds() {
        val joinRequest = MemberRegisterReq("신한은행", "111-111-111111", "LGU+", "010-1234-5678", "aaaabbbbaa")
        val reqBody = objectMapper.writeValueAsString(joinRequest)

        val result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("$API_URI_PREFIX/member")
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
                        PayloadDocumentation.fieldWithPath("accountBank").type(JsonFieldType.STRING)
                            .description("계좌 은행"),
                        PayloadDocumentation.fieldWithPath("accountNum").type(JsonFieldType.STRING)
                            .description("계좌 번호"),
                        PayloadDocumentation.fieldWithPath("mobileCarrier").type(JsonFieldType.STRING)
                            .description("휴대폰 통신사 이름"),
                        PayloadDocumentation.fieldWithPath("phoneNum").type(JsonFieldType.STRING)
                            .description("휴대폰 번호('-' 기호 포함)"),
                        PayloadDocumentation.fieldWithPath("passwd").type(JsonFieldType.STRING)
                            .description("비밀번호(알파벳 3종류 이상 8자리 문자열, 혹은 2종류 이상 10자리 이내의 문자열)"),
                    ),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN)
                            .description("회원가입 성공 여부")
                    )
                )
            )
    }
}
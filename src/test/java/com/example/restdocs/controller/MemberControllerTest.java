package com.example.restdocs.controller;

import com.example.restdocs.domain.Member;
import com.example.restdocs.dto.MemberCreateForm;
import com.example.restdocs.dto.MemberModifyRequest;
import com.example.restdocs.service.MemberService;
import com.example.restdocs.support.docs.RestDocsTestSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static com.example.restdocs.config.RestDocsConfig.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MemberController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs // rest docs 자동 설정
class MemberControllerTest extends RestDocsTestSupport {

//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private MemberService memberService;

    //    @Test
//    void save() throws Exception {
//        mvc.perform(post("/api/members")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(memberCreateForm()))
//                ).andExpect(status().isOk())
//                .andDo(
//                        // rest docs 문서 작성 시작
//                        document("member-save", // 문서 조각 디렉토리 명
//                                requestFields(
//                                        fieldWithPath("email").description("email").attributes(Attributes.key("constraint").value("좋은 제목 입력해주세요.")),
//                                        fieldWithPath("name").description("name")
//                                )
//                        )
//                )
//                .andDo(print());
//    }
//    @Test
//    void member_get() throws Exception {
//        Member member = Member.builder().id(1L).email("jisu@naver.com").name("김지수").build();
//        given(memberService.getMember(anyLong())).willReturn(member);
//        // 조회 API -> 대상의 데이터가 있어야 합니다.
//        mvc.perform(
//                        get("/api/members/{memberId}", 1L)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andDo( // rest docs 문서 작성 시작
//                        document("member-get", // 문서 조각 디렉토리 명
//                                pathParameters( // path 파라미터 정보 입력
//                                        parameterWithName("memberId").description("memberId")
//                                ),
//                                responseFields( // response 필드 정보 입력
//                                        fieldWithPath("id").description("ID"),
//                                        fieldWithPath("email").description("email"),
//                                        fieldWithPath("name").description("name")
//                                )
//                        )
//                );
//    }

    @DisplayName("v2 멤버 저장")
    @Test
    void save() throws Exception {
        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(memberCreateForm()))
                ).andExpect(status().isOk())
                .andDo(
                        // rest docs 문서 작성 시작
                        restDocs.document(
                                requestFields(
                                        fieldWithPath("email").description("email").attributes(field("constraints", "길이 30 이하")),
                                        fieldWithPath("name").description("name").attributes(field("constraints", "길이 10 이하"))
                                )
                        )
                )
                .andDo(print());
    }

    @DisplayName("v2 단일 멤버 조회")
    @Test
    void member_get() throws Exception {
        Member member = Member.builder().id(1L).email("jisu@naver.com").name("김지수").build();
        given(memberService.getMember(anyLong())).willReturn(member);
        // 조회 API -> 대상의 데이터가 있어야 합니다.
        mockMvc.perform(
                        get("/api/members/{memberId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo( // rest docs 문서 작성 시작
                        restDocs.document(
                                pathParameters( // path 파라미터 정보 입력
                                        parameterWithName("memberId").optional().description("Long")
                                ),
                                responseFields( // response 필드 정보 입력
                                        fieldWithPath("id").description("ID"),
                                        fieldWithPath("email").description("email"),
                                        fieldWithPath("name").description("name")
                                )
                        )
                );
    }

    @DisplayName("v2 멤버 페이징 조회")
    @Test
    void memberFindAll() throws Exception {
        Member member = Member.builder().id(1L).email("jisu@naver.com").name("김지수").build();
        PageImpl<Member> memberPage = new PageImpl<>(List.of(member), PageRequest.of(0, 10), 1);
        given(memberService.findAll(ArgumentMatchers.any(Pageable.class))).willReturn(memberPage);

        mockMvc.perform(
                        get("/api/members")
                                .param("size", "10")
                                .param("page", "0")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("size").optional().description("size"), // 필수여부 false
                                        parameterWithName("page").optional().description("page") // 필수여부 false
                                )
                        )
                )
        ;
    }

    @DisplayName("v2 멤버 수정")
    @Test
    void memberModify() throws Exception {
        // given
        Long memberId = 1L;
        Member member = Member.builder().id(1L).email("jisu@naver.com").name("김지수2").build();
        MemberModifyRequest memberModifyRequest = memberModifyRequest();
        given(memberService.modifyMember(memberId, memberModifyRequest)).willReturn(member);


        mockMvc.perform(
                        patch("/api/members/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(memberModifyRequest())))
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("id").description("Member ID")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("name").attributes(field("constraints", "길이 10 이하"))
                                )
                        )
                );
    }


    private MemberCreateForm memberCreateForm() {
        return MemberCreateForm.of("jisu@email.com", "김지수");
    }
    private MemberModifyRequest memberModifyRequest() {
        return MemberModifyRequest.of("김지수2");
    }
}
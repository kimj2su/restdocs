package com.example.restdocs.controller;

import com.example.restdocs.domain.Member;
import com.example.restdocs.dto.MemberCreateForm;
import com.example.restdocs.dto.MemberModifyRequest;
import com.example.restdocs.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @GetMapping
    public Page<Member> findAll(@PageableDefault(size = 10) Pageable pageable) {
        return memberService.findAll(pageable);
    }

    @PostMapping
    public Member saveMember(@RequestBody MemberCreateForm memberCreateForm) {
        return memberService.save(memberCreateForm);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Member> modify(@PathVariable Long id, @RequestBody MemberModifyRequest dto){
        return new ResponseEntity<>(memberService.modifyMember(id, dto), null, HttpStatus.OK);
    }

    @GetMapping("/")
    public String home() {
        return "hello";
    }
}

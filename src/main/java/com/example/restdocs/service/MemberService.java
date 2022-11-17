package com.example.restdocs.service;

import com.example.restdocs.domain.Member;
import com.example.restdocs.dto.MemberCreateForm;
import com.example.restdocs.dto.MemberModifyRequest;
import com.example.restdocs.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member save(MemberCreateForm memberCreateForm) {
        Member member = Member.builder().email(memberCreateForm.getEmail()).name(memberCreateForm.getName()).build();
        return memberRepository.save(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));
    }

    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Transactional
    public Member modifyMember(Long memberId, MemberModifyRequest dto) {
        Member member = memberRepository.getReferenceById(memberId);
        try {
            member.modifyName(dto.getName());
            memberRepository.flush();
            return member;
        } catch (EntityNotFoundException e) {
            log.error("에러");
            return null;
        }

    }
}

package com.example.restdocs.service;

import com.example.restdocs.domain.Member;
import com.example.restdocs.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
    }

}

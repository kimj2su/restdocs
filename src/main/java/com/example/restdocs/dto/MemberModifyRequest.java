package com.example.restdocs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberModifyRequest {
    private String name;
    public MemberModifyRequest() {

    }

    public MemberModifyRequest(String name) {
        this.name = name;
    }

    public static MemberModifyRequest of(String name) {
        return new MemberModifyRequest(name);
    }
}

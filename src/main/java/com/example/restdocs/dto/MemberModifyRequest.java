package com.example.restdocs.dto;

import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class MemberModifyRequest {
    private final String name;

    @ConstructorProperties({"name"})
    public MemberModifyRequest(String name) {
        this.name = name;
    }

    public static MemberModifyRequest of(String name) {
        return new MemberModifyRequest(name);
    }
}

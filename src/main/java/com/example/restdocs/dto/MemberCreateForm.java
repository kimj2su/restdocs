package com.example.restdocs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateForm {

    private final String email;
    private final String name;

    public MemberCreateForm(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static MemberCreateForm of(String email, String name) {
       return new MemberCreateForm(email, name);
    }
}

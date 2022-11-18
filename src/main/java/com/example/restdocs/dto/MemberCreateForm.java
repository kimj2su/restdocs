package com.example.restdocs.dto;

import com.example.restdocs.domain.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class MemberCreateForm {

    private final String email;
    private final String name;
    private final MemberStatus status;

    @ConstructorProperties({"email, name, status"})
    public MemberCreateForm(String email, String name, MemberStatus status) {
        this.email = email;
        this.name = name;
        this.status = status;
    }

    public static MemberCreateForm of(String email, String name, MemberStatus status) {
       return new MemberCreateForm(email, name, status);
    }
}

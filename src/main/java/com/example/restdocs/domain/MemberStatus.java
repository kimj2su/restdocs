package com.example.restdocs.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberStatus implements EnumType{
    LOCK("일시정지"),
    NOMAL("정상"),
    BAN("정지");

    private final String description;

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}

package com.example.restdocs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex implements EnumType{
    MALE("남자"),
    FEMALE("여자");

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

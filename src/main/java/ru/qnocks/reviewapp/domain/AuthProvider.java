package ru.qnocks.reviewapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {

    LOCAL("local"),

    GOOGLE("google"),

    GITHUB("github");

    private final String name;
}

package ru.qnocks.reviewapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Content {

    BOOK("book"),

    MOVIE("movie"),

    GAME("game");

    private final String name;
}

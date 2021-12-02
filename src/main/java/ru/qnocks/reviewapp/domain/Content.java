package ru.qnocks.reviewapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@Getter
@AllArgsConstructor
public enum Content {

    BOOK("book"),

    MOVIE("movie"),

    GAME("game");

    @Field(name = "name", index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private final String name;
}

package ru.qnocks.reviewapp.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "reviews")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {

    private String id;

    private String name;

    private Set<ReviewDto> reviews = new HashSet<>();
}

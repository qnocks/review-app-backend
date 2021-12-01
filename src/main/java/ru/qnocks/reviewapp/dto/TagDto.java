package ru.qnocks.reviewapp.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Long id;

    private String name;

//    @JsonBackReference
    private Set<ReviewDto> reviews = new HashSet<>();
}

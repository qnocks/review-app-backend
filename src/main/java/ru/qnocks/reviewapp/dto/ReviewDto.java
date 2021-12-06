package ru.qnocks.reviewapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.qnocks.reviewapp.domain.Content;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "tags")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private String name;

    private Content content;

    private String contentName;

    private String text;

    private Integer score;

    private Double rating;

    private Integer likes;

    private String imagesLink;

    private Long userId;

    @JsonIgnoreProperties("reviews")
    private Set<TagDto> tags = new HashSet<>();
}

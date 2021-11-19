package ru.qnocks.reviewapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.qnocks.reviewapp.domain.Content;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private String name;

    private Content content;

    private String contentName;

    private String text;

    private Integer score;

    private Double rating;

    private Integer likes;

    private String imagesLink;
}

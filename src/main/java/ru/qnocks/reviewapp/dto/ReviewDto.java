package ru.qnocks.reviewapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.qnocks.reviewapp.domain.Content;

import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tags")
@EqualsAndHashCode(of = "id")
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

    @JsonManagedReference
    private Set<TagDto> tags = new HashSet<>();
}

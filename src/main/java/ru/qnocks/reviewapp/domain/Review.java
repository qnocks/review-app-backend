package ru.qnocks.reviewapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "content")
    private Content content;

    @Column(name = "content_name")
    private String contentName;

    @OneToMany(mappedBy = "review")
    private Set<Comment> comments;

    @Column(name = "text")
    private String text;

    @Column(name = "score")
    private Integer score;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "images_link")
    private String imagesLink;

    @ManyToMany
    @JoinTable(
            name = "review_tag",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags;
}

package ru.qnocks.reviewapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Table(name = "review")
@AnalyzerDef(
    name = "eng",
    tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
    filters = {
        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
        @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                @Parameter(name = "language", value = "English")
        })
    }
)
@AnalyzerDef(
        name = "rus",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @Parameter(name = "language", value = "Russian")
                })
        }
)
@Getter
@Setter
@ToString(exclude = {"user", "tags"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
//    @Analyzer(definition = "eng")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "content")
//    @Analyzer(definition = "eng")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private Content content;

    @Column(name = "content_name")
//    @Analyzer(definition = "eng")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String contentName;

    @Length(max = 100000)
    @Column(name = "txt")
//    @Analyzer(definition = "eng")
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String text;

    @Column(name = "score")
    private Integer score;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "images_link")
    private String imagesLink;

//    @OneToMany(mappedBy = "review")
//    private Set<Comment> comments;

//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.EAGER, optional = false)

//    @JsonIgnore
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnoreProperties("reviews")
    @IndexedEmbedded
    @ManyToMany
    @JoinTable(
            name = "review_tag",
            joinColumns = @JoinColumn(name = "review_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();
}

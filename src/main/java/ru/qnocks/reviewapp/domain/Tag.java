package ru.qnocks.reviewapp.domain;

import lombok.*;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
//@Data
@Getter
@Setter
@ToString(exclude = "reviews")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Field(name = "name", index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Review> reviews = new HashSet<>();
}

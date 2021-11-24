package ru.qnocks.reviewapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "email")
    private String email;

    private String imageUrl;

    @NonNull
//    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
            (name = "user_role",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "usr_id")
    private Set<Review> reviews = new HashSet<>();
}

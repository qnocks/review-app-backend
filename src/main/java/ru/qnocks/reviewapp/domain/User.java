package ru.qnocks.reviewapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    private String imageUrl;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @ManyToMany
    @JoinTable
            (name = "user_role",
            joinColumns = @JoinColumn(name = "usr_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}

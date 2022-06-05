package com.project.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    @Transient
    private String confirmPassword;
    private String nickname;
    private String userDescription;
    private float wallet;
    private String profilePicture;
    @OneToMany(mappedBy = "creator")
    private Set<Picture> createdPictures;
    @OneToMany(mappedBy = "owner")
    private Set<Picture> ownPictures;
    @OneToMany(mappedBy = "customer")
    private Set<Offer> sentOffers;
    @OneToMany(mappedBy = "salesman")
    private Set<Offer> receivedOffers;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
}
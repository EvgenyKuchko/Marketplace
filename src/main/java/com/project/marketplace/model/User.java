package com.project.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @OneToMany(mappedBy = "creator")
    private Set<Picture> createdPictures;
    @OneToMany(mappedBy = "owner")
    private Set<Picture> ownPictures;
    @OneToMany(mappedBy = "customer")
    private Set<Offer> sentOffers;
    @OneToMany(mappedBy = "owner")
    private Set<Offer> receivedOffers;
}
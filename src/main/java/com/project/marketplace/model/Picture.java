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
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String filePath;
    private float price;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    @OneToMany(mappedBy = "picture")
    private Set<Offer> offers;
}
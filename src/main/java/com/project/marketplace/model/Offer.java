package com.project.marketplace.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float amount;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id", nullable = false)
    private Picture picture;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salesman_id", nullable = false)
    private User salesman;
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
}
package com.project.marketplace.repository;

import com.project.marketplace.model.Offer;
import com.project.marketplace.model.OfferStatus;
import com.project.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Modifying
    @Query("update Offer o set o.status = :status where o.id = :id")
    void updateStatus(@Param("status") OfferStatus status, @Param("id") long id);

    List<Offer> findAllByStatusAndCustomer(OfferStatus status, User customer);

    List<Offer> findAllByStatusAndSalesman(OfferStatus status, User salesman);
}
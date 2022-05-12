package com.project.marketplace.repository;

import com.project.marketplace.model.Picture;
import com.project.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Modifying
    @Query("update Picture p set p.price = :price where p.id = :id")
    void changePrice(@Param("price") Float price, @Param("id") long id);

    @Modifying
    @Query("update Picture p set p.owner = :owner where p.id = :id")
    void changeOwner(@Param("owner") User owner, @Param("id") long id);

    List<Picture> findAllByOwner(User owner);

    List<Picture> findAllByCreator(User creator);
}
package com.project.marketplace.repository;

import com.project.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    User findByEmail(String email);

    User findByNickname(String nickname);

    @Modifying
    @Query("update User u set u.wallet = :wallet where u.id = :id")
    void updateWallet(@Param("wallet") Float wallet, @Param("id") long id);

    @Modifying
    @Query("update User u set u.userDescription = :description where u.nickname = :nickname")
    void updateDescription(@Param("description") String description, @Param("nickname") String nickname);
}
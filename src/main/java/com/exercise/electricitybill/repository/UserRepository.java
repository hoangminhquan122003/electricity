package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("SELECT COUNT(u)>0 FROM USER u WHERE u.username= :username ")
    boolean existsByUsername(@Param("username") String username);
    @Query("SELECT COUNT(u)>0 FROM USER u WHERE u.email= :email ")
    boolean existsByEmail( @Param("email") String email);
    @Query("SELECT u FROM USER u WHERE u.username= :username ")
    Optional<User> findByUsername(@Param("username") String username);

}

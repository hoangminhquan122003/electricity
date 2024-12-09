package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Integer> {
    @Query("SELECT o from OTP o WHERE o.email= :email")
    Optional<OTP> findByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM OTP o WHERE o.email = :email")
    void deleteByEmail( @Param("email") String email);

    @Modifying
    @Query("DELETE FROM OTP o WHERE o.expirationAt < :date")
    void deleteByExpirationAtBefore(@Param("date") LocalDateTime date);
}

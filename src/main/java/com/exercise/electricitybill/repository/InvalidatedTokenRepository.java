package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.InvalidedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidedToken,String> {
    int deleteByExpirationTimeBefore(Date date);
}

package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.InvalidedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidedToken,String> {
    @Modifying
    @Query("DELETE FROM INVALIDEDTOKEN i WHERE i.date < :date")
    int deleteByExpirationTimeBefore( @Param("") Date date);
}

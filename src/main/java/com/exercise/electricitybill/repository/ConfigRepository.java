package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Integer> {
    @Query("SELECT COUNT(c)>0 FROM CONFIG c WHERE c.configName= :configName ")
    boolean existsByConfigName(@Param("configName") String configName);
}

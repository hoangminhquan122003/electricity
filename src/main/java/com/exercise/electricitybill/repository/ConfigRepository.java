package com.exercise.electricitybill.repository;

import com.exercise.electricitybill.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Integer> {
    boolean existsByConfigName(String configName);
}

package com.alex.buildindingAndco.repository;

import com.alex.buildindingAndco.model.Worksite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorksiteRepository extends JpaRepository <Worksite, Integer> {
}

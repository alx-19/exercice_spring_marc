package com.alex.buildindingAndco.repository;

import com.alex.buildindingAndco.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository <Technician, Integer> {
}

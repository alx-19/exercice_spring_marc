package com.alex.buildindingAndco.repository;

import com.alex.buildindingAndco.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository <Manager, Integer> {
}

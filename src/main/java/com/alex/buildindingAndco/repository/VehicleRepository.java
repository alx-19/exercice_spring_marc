package com.alex.buildindingAndco.repository;

import com.alex.buildindingAndco.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, Integer> {
}

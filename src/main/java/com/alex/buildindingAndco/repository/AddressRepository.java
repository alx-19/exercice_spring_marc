package com.alex.buildindingAndco.repository;

import com.alex.buildindingAndco.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository <Address, Integer> {
}

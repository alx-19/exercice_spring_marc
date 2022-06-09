package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.Vehicle;

import java.util.List;

public interface VehicleService {

    // retourne tout /GET
    List<Vehicle> getAll();

    // retourne par id /GET
    Vehicle getById(Integer id);

    // créer Vehicle /POST
    Vehicle createVehicle(Vehicle vehicle);

    // suppression Vehicle /DELETE
    void deleteVehicle(Integer id);

    // modification Vehicle /PUT
    Vehicle updateVehicle(Vehicle vehicle);
}

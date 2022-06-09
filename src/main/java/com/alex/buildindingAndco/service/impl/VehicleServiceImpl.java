package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.Vehicle;
import com.alex.buildindingAndco.repository.VehicleRepository;
import com.alex.buildindingAndco.service.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAll() {
        return this.vehicleRepository.findAll(Sort.by("model").ascending());
    }

    @Override
    public Vehicle getById(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No vehicle found for the given ID"));
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        log.debug("attempting to save vehicle in DB ... ");
        return this.vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Integer id) {
        log.debug("delete to vehicle in DB ... ");
        Vehicle vehicleToDelete = this.getById(id);
        if (this.canDeleteVehicle(vehicleToDelete)) {
            this.vehicleRepository.delete(vehicleToDelete);
        } else {
            throw new NotAllowedToDeleteException("The given vehicle still has with a person.");
        }
    }

    private boolean canDeleteVehicle(Vehicle vehicle) {
        return (null == vehicle.getTechnician());
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        log.debug("Attempting to update an vehicle {}", vehicle.getId());
        Vehicle existingVehicle = this.getById(vehicle.getId());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setNumberPlate(vehicle.getNumberPlate());
        existingVehicle.setYearOfConstruction(vehicle.getYearOfConstruction());
        existingVehicle.setTechnician(vehicle.getTechnician());
        return this.vehicleRepository.save(existingVehicle);
    }
}

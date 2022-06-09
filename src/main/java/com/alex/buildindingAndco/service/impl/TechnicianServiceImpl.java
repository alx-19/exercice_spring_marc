package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.model.Technician;
import com.alex.buildindingAndco.repository.TechnicianRepository;
import com.alex.buildindingAndco.service.TechnicianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    Logger log = LoggerFactory.getLogger(TechnicianServiceImpl.class);

    @Autowired
    private TechnicianRepository technicianRepository;

    @Override
    public List<Technician> getAll() {
        return this.technicianRepository.findAll(Sort.by("lastName").ascending());
    }

    @Override
    public Technician getById(Integer id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No technician found for the given ID"));
    }

    @Override
    public Technician createTechnician(Technician technician) {
        log.debug("attempting to save technician in DB ... ");
        return this.technicianRepository.save(technician);
    }

    @Override
    public void deleteTechnician(Integer id) {
        log.debug("delete to technician in DB ... ");
        Technician technicianToDelete = this.getById(id);
        if (this.canDeleteTechnician(technicianToDelete)) {
            this.technicianRepository.delete(technicianToDelete);
        } else {
            throw new NotAllowedToDeleteException("The given technician still has with a person.");
        }
    }

    private boolean canDeleteTechnician(Technician technician) {
        return (null == technician.getWorksites() || technician.getWorksites().isEmpty());
    }

    @Override
    public Technician updateTechnician(Technician technician) {
        log.debug("Attempting to update an technician {}", technician.getId());
        Technician existingAddress = this.getById(technician.getId());
        existingAddress.setAge(technician.getAge());
        existingAddress.setFirstName(technician.getFirstName());
        existingAddress.setLastName(technician.getLastName());
        existingAddress.setAge(technician.getAge());
        existingAddress.setAddress(technician.getAddress());
        existingAddress.setManager(technician.getManager());
        existingAddress.setVehicle(technician.getVehicle());
        existingAddress.setWorksites(technician.getWorksites());
        return this.technicianRepository.save(existingAddress);
    }
}

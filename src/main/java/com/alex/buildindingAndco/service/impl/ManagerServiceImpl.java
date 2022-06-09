package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.Manager;
import com.alex.buildindingAndco.repository.ManagerRepository;
import com.alex.buildindingAndco.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<Manager> getAll() {
        return this.managerRepository.findAll(Sort.by("lastName").ascending());
    }

    @Override
    public Manager getById(Integer id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No manager found for the given ID"));
    }

    @Override
    public Manager createManager(Manager manager) {
        log.debug("attempting to save manager in DB ... ");
        return this.managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Integer id) {
        Manager managerToDelete = this.getById(id);
        if (this.canDeleteManager(managerToDelete)) {
            this.managerRepository.delete(managerToDelete);
        } else {
            throw new NotAllowedToDeleteException("The given manager still has with a person.");
        }
    }

    private boolean canDeleteManager(Manager manager) {
        return (null == manager.getTechnicians() || manager.getTechnicians().isEmpty());
    }

    @Override
    public Manager updateManager(Manager manager) {
        log.debug("Attempting to update an manager {}", manager.getId());
        Manager existingManager = this.getById(manager.getId());
        existingManager.setFirstName(manager.getFirstName());
        existingManager.setLastName(manager.getLastName());
        existingManager.setPhone(manager.getPhone());
        existingManager.setMobile(manager.getMobile());
        existingManager.setTechnicians(manager.getTechnicians());
        return this.managerRepository.save(existingManager);
    }
}

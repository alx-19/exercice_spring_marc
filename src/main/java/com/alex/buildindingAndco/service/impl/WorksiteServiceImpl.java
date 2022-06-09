package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.Worksite;
import com.alex.buildindingAndco.repository.WorksiteRepository;
import com.alex.buildindingAndco.service.WorksiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorksiteServiceImpl implements WorksiteService {

    Logger log = LoggerFactory.getLogger(WorksiteServiceImpl.class);

    @Autowired
    private WorksiteRepository worksiteRepository;

    @Override
    public List<Worksite> getAll() {
        return this.worksiteRepository.findAll(Sort.by("name").ascending());
    }

    @Override
    public Worksite getById(Integer id) {
        return worksiteRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No worksite found for the given ID"));
    }

    @Override
    public Worksite createWorksite(Worksite worksite) {
        log.debug("attempting to save worksite in DB ... ");
        return this.worksiteRepository.save(worksite);
    }

    @Override
    public void deleteWorksite(Integer id) {
        log.debug("delete to worksite in DB ... ");
        Worksite worksiteToDelete = this.getById(id);
        if (this.canDeleteWorksite(worksiteToDelete)) {
            this.worksiteRepository.delete(worksiteToDelete);
        } else {
            throw new NotAllowedToDeleteException("The given worksite still has with a person.");
        }
    }

    private boolean canDeleteWorksite(Worksite worksite) {
        return (null == worksite.getTechnicians());
    }

    @Override
    public Worksite updateWorksite(Worksite worksite) {
        log.debug("Attempting to update an worksite {}", worksite.getId());
        Worksite existingWorksite = this.getById(worksite.getId());
        existingWorksite.setAddress(worksite.getAddress());
        existingWorksite.setName(worksite.getName());
        existingWorksite.setPrice(worksite.getPrice());
        existingWorksite.setTechnicians(worksite.getTechnicians());
        return this.worksiteRepository.save(existingWorksite);
    }
}

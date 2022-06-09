package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.Technician;

import java.util.List;

public interface TechnicianService {

    // retourne tout /GET
    List<Technician> getAll();

    // retourne par id /GET
    Technician getById(Integer id);

    // créer Technician /POST
    Technician createTechnician(Technician technician);

    // suppression Technician /DELETE
    void deleteTechnician(Integer id);

    // modification Technician /PUT
    Technician updateTechnician(Technician technician);
}

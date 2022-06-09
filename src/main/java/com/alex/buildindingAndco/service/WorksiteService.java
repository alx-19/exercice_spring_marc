package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.Worksite;

import java.util.List;

public interface WorksiteService {

    // retourne tout /GET
    List< Worksite> getAll();

    // retourne par id /GET
     Worksite getById(Integer id);

    // créer Worksite /POST
     Worksite createWorksite( Worksite worksite);

    // suppression Worksite /DELETE
    void deleteWorksite(Integer id);

    // modification Worksite /PUT
     Worksite updateWorksite( Worksite worksite);
}

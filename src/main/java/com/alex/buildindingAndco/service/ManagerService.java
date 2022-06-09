package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.Manager;

import java.util.List;

public interface ManagerService {

    // retourne tout /GET
    List<Manager> getAll();

    // retourne par id /GET
    Manager getById(Integer id);

    // créer Manager /POST
    Manager createManager(Manager manager);

    // suppression Manager /DELETE
    void deleteManager(Integer id);

    // modification Manager /PUT
    Manager updateManager(Manager manager);
}

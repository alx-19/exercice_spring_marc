package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.Address;

import java.util.List;

public interface AddressService {

    // retourne tout /GET
    List<Address> getAll();

    // retourne par id /GET
    Address getById(Integer id);

    // créer adresse /POST
    Address createAddress(Address address);

    // suppression adresse /DELETE
    void deleteAddress(Integer id);

    // modification adresse /PUT
    Address updateAddress(Address address);
}

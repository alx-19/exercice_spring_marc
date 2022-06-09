package com.alex.buildindingAndco.service.impl;

import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.repository.AddressRepository;
import com.alex.buildindingAndco.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;


    /*
     all addresses in the BDD
     */
    @Override
    public List<Address> getAll() {
        return this.addressRepository.findAll(Sort.by("city").ascending());
    }

    /*
     retrieving an address with its id
     */
    @Override
    public Address getById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No address found for the given ID"));
    }

    /*
     creating an address
     */
    @Override
    public Address createAddress(Address address) {
        log.debug("attempting to save address in DB ... ");
        return this.addressRepository.save(address);
    }

    /*
    deletion of an address unless it is linked to a technician
     */
    @Override
    public void deleteAddress(Integer id) {
        log.debug("delete to address in DB ... ");
        Address addressToDelete = this.getById(id);
        if (this.canDeleteAddress(addressToDelete)) {
            this.addressRepository.delete(addressToDelete);
        } else {
            throw new NotAllowedToDeleteException("The given address still has with a person.");
        }
    }

    /*
    verification of the belonging of an address to a technician
     */
    private boolean canDeleteAddress(Address address) {
        return (null == address.getTechnicians() || address.getTechnicians().isEmpty());
    }

    @Override
    public Address updateAddress(Address address) {
        log.debug("Attempting to update an address {}", address.getId());
        Address existingAddress = this.getById(address.getId());
        existingAddress.setNumber(address.getNumber());
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setTechnicians(address.getTechnicians());
        existingAddress.setWorksite(address.getWorksite());
        return this.addressRepository.save(existingAddress);
    }
}

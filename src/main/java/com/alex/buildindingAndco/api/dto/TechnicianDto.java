package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.model.Manager;
import com.alex.buildindingAndco.model.Vehicle;
import com.alex.buildindingAndco.model.Worksite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Long age;
    private Manager manager;
    private Vehicle vehicle;
    private Address address;
    Set<Integer> worksitesId;
}

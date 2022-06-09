package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.model.Manager;
import com.alex.buildindingAndco.model.Vehicle;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicianDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Long age;
    private ManagerDto manager;
    private VehicleDto vehicle;
    private AddressDto address;
    Set<Integer> worksitesId;
}

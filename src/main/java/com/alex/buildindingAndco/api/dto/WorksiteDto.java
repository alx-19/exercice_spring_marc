package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.model.Worksite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorksiteDto {

    private Integer id;
    private String name;
    private Double price;
    private Address address;
    Set<Integer> techniciansId;

}

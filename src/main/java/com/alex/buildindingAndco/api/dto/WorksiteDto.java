package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Address;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorksiteDto {

    private Integer id;
    private String name;
    private Double price;
    private AddressDto address;
    Set<Integer> techniciansId;

}

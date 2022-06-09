package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Worksite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;
    private Long number;
    private String street;
    private String city;
    private Set<Integer> techniciansId;
    private Integer worksiteId;
}

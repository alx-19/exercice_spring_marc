package com.alex.buildindingAndco.api.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
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

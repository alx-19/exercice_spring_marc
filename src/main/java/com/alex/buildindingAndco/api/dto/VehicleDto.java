package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Technician;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Integer id;
    private String NumberPlate;
    private String model;
    private Long yearOfConstruction;
    private Integer technicianId;
}

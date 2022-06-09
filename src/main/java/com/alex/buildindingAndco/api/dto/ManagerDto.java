package com.alex.buildindingAndco.api.dto;

import com.alex.buildindingAndco.model.Technician;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private Long phone;
    private Long mobile;
    Set<Integer> techniciansId;
}

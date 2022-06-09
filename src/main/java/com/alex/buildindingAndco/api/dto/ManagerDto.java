package com.alex.buildindingAndco.api.dto;

import lombok.*;

import java.util.Set;

@Setter
@Getter
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

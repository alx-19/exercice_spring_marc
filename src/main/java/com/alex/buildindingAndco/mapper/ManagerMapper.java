package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.ManagerDto;
import com.alex.buildindingAndco.model.Manager;
import com.alex.buildindingAndco.model.Technician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerMapper {

    @Mapping(target = "techniciansId", expression = "java(getTechniciansToDto(manager.getTechnicians()))")
    ManagerDto mapToDto (Manager manager);
    default Set <Integer> getTechniciansToDto(Set <Technician> technicians) {
        return technicians.stream().map(Technician::getId).collect(Collectors.toSet());
    }

    @Mapping(target = "technicians", expression = "java(getTechniciansToModel(managerDto.getTechniciansId()))")
    Manager mapToModel(ManagerDto managerDto);
    default Set <Technician> getTechniciansToModel(Set <Integer> techniciansId) {
        return techniciansId.stream().map(Technician::new).collect(Collectors.toSet());
    }
}

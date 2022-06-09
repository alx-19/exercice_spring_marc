package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.WorksiteDto;
import com.alex.buildindingAndco.model.Technician;
import com.alex.buildindingAndco.model.Worksite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface WorksiteMapper {

    @Mapping(target = "techniciansId", expression = "java(getTechniciansToDto(worksite.getTechnicians()))")
    WorksiteDto mapToDto (Worksite worksite);
    default Set <Integer> getTechniciansToDto(Set <Technician> technicians) {
        return technicians.stream().map(Technician::getId).collect(Collectors.toSet());
    }

    @Mapping(target = "technicians", expression = "java(getTechniciansToModel(worksiteDto.getTechniciansId()))")
    Worksite mapToModel(WorksiteDto worksiteDto);
    default Set <Technician> getTechniciansToModel(Set <Integer> techniciansId) {
        return techniciansId.stream().map(Technician::new).collect(Collectors.toSet());
    }
}

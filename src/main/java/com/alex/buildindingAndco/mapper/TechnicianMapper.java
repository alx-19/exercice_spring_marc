package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.TechnicianDto;
import com.alex.buildindingAndco.model.Technician;
import com.alex.buildindingAndco.model.Worksite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE, uses = {ManagerMapper.class, VehicleMapper.class, AddressMapper.class})
public interface TechnicianMapper {

    @Mapping(target = "worksitesId", expression = "java(getWorksitesToDto(technician.getWorksites()))")
    TechnicianDto mapToDto (Technician technician);
    default Set <Integer> getWorksitesToDto(Set <Worksite> worksites) {
        return worksites.stream().map(Worksite::getId).collect(Collectors.toSet());
    }
    @Mapping(target = "worksites", expression = "java(getWorksiteToModel(technicianDto.getWorksitesId()))")
    Technician mapToModel(TechnicianDto technicianDto);
    default Set <Worksite> getWorksiteToModel(Set <Integer> worksitesId) {
        return worksitesId.stream().map(Worksite::new).collect(Collectors.toSet());
    }
}

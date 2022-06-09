package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.AddressDto;
import com.alex.buildindingAndco.model.Address;
import com.alex.buildindingAndco.model.Technician;
import com.alex.buildindingAndco.model.Worksite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    @Mapping(target = "techniciansId", expression = "java(getTechniciansToDto(address.getTechnicians()))")
    @Mapping(target = "worksiteId", source = "worksite.id")
    AddressDto mapToDto(Address address);

    default Set <Integer> getTechniciansToDto(Set <Technician> technicians) {
        return technicians.stream().map(Technician::getId).collect(Collectors.toSet());
    }


    @Mapping(target = "technicians", expression = "java(getTechniciansToModel(addressDto.getTechniciansId()))")
    @Mapping(target = "worksite", expression = "java(getWorksiteToModel(addressDto.getWorksiteId()))")
    Address mapToModel(AddressDto addressDto);

    default Set <Technician> getTechniciansToModel(Set <Integer> techniciansId) {
        return techniciansId.stream().map(Technician::new).collect(Collectors.toSet());
    }
    default Worksite getWorksiteToModel(Integer worksitesId) {
        return new Worksite(worksitesId);
    }
}

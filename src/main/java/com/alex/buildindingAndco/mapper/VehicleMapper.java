package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.TechnicianDto;
import com.alex.buildindingAndco.api.dto.VehicleDto;
import com.alex.buildindingAndco.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE, uses = {TechnicianMapper.class})
public interface VehicleMapper {

    @Mapping(target = "technicianId", source = "technician.id")
    VehicleDto mapToDto (Vehicle vehicle);
    Vehicle mapToModel(VehicleDto vehicleDto);
}

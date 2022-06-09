package com.alex.buildindingAndco.mapper;

import com.alex.buildindingAndco.api.dto.VehicleDto;
import com.alex.buildindingAndco.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface VehicleMapper {

    VehicleDto mapToDto (Vehicle vehicle);

    Vehicle mapToModel(VehicleDto vehicleDto);
}

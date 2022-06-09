package com.alex.buildindingAndco.api.v1;

import com.alex.buildindingAndco.api.dto.VehicleDto;
import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.mapper.VehicleMapper;
import com.alex.buildindingAndco.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/vehicles")
public class VehicleApi {

    Logger log = LoggerFactory.getLogger(VehicleApi.class);

    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    public VehicleApi(VehicleService vehicleService, VehicleMapper vehicleMapper) {
        this.vehicleService = vehicleService;
        this.vehicleMapper = vehicleMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Return the list of all vehicles by city ascending.")
    public ResponseEntity<List<VehicleDto>> getAll() {
        log.info("Retrieving vehiclees...");

        return ResponseEntity.ok(
                this.vehicleService.getAll().stream()
                        .map(this.vehicleMapper::mapToDto)
                        .toList()
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Trying to retrieve a vehicle from the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return an vehicle found the given ID"),
            @ApiResponse(responseCode = "404", description = "No vehicle found the given ID")
    })
    public ResponseEntity<VehicleDto> getById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(
                    this.vehicleMapper
                            .mapToDto(this.vehicleService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    @Operation(summary = "Create a vehicle")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody final VehicleDto vehicleDto) {
        log.debug("Attempting to create vehicle with model {}", vehicleDto.getModel());

        VehicleDto vehicleDtoResponse =
                this.vehicleMapper.mapToDto(
                        this.vehicleService.createVehicle(
                                this.vehicleMapper.mapToModel(vehicleDto)
                        ));

        return ResponseEntity
                .created(URI.create("/v1/vehicles/" + vehicleDtoResponse.getId()))
                .body(vehicleDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a vehicle for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No vehicle found the given ID"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the vehicle for the given ID")
    })
    public ResponseEntity<Void> deleteVehicle(@PathVariable final Integer id) {
        log.debug("Attemtping to delete a vehicle with id {}", id);
        try {
            this.vehicleService.deleteVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Void> updateVehicle(@PathVariable final Integer id, @RequestBody VehicleDto vehicleDto) {
        try {
            log.debug("Updating vehicle {}", vehicleDto.getId());
            vehicleDto.setId(id);
            this.vehicleService.updateVehicle(vehicleMapper.mapToModel(vehicleDto));
            log.debug("Successfully updated vehicle {}", vehicleDto.getId());

            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }


}

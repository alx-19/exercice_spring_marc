package com.alex.buildindingAndco.api.v1;

import com.alex.buildindingAndco.api.dto.ManagerDto;
import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.mapper.ManagerMapper;
import com.alex.buildindingAndco.service.ManagerService;
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
@RequestMapping("/v1/managers")
public class ManagerApi {

    Logger log = LoggerFactory.getLogger(ManagerApi.class);

    private final ManagerService managerService;
    private final ManagerMapper managerMapper;

    public ManagerApi(ManagerService managerService, ManagerMapper managerMapper) {
        this.managerService = managerService;
        this.managerMapper = managerMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Return the list of all manageres by city ascending.")
    public ResponseEntity<List<ManagerDto>> getAll() {
        log.info("Retrieving manageres...");

        return ResponseEntity.ok(
                this.managerService.getAll().stream()
                        .map(this.managerMapper::mapToDto)
                        .toList()
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Trying to retrieve a manager from the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return an Manager found the given ID"),
            @ApiResponse(responseCode = "404", description = "No Manager found the given ID")
    })
    public ResponseEntity<ManagerDto> getById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(
                    this.managerMapper
                            .mapToDto(this.managerService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    @Operation(summary = "Create a manager")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<ManagerDto> createManager(@RequestBody final ManagerDto managerDto) {
        log.debug("Attempting to create manager with lastname {}", managerDto.getLastName());

        ManagerDto managerDtoResponse =
                this.managerMapper.mapToDto(
                        this.managerService.createManager(
                                this.managerMapper.mapToModel(managerDto)
                        ));

        return ResponseEntity
                .created(URI.create("/v1/managers/" + managerDtoResponse.getId()))
                .body(managerDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a manager for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No manager found the given ID"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the manager for the given ID")
    })
    public ResponseEntity<Void> deleteManager(@PathVariable final Integer id) {
        log.debug("Attemtping to delete a Manager with id {}", id);
        try {
            this.managerService.deleteManager(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a manager")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Void> updateManager(@PathVariable final Integer id, @RequestBody ManagerDto managerDto) {
        try {
            log.debug("Updating manager {}", managerDto.getId());
            managerDto.setId(id);
            this.managerService.updateManager(managerMapper.mapToModel(managerDto));
            log.debug("Successfully updated manager {}", managerDto.getId());

            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }


}

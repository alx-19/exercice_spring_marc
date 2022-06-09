package com.alex.buildindingAndco.api.v1;

import com.alex.buildindingAndco.api.dto.WorksiteDto;
import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.mapper.WorksiteMapper;
import com.alex.buildindingAndco.service.WorksiteService;
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
@RequestMapping("/v1/worksites")
public class WorksiteApi {

    Logger log = LoggerFactory.getLogger(WorksiteApi.class);

    private final WorksiteService worksiteService;
    private final WorksiteMapper worksiteMapper;

    public WorksiteApi(WorksiteService worksiteService, WorksiteMapper worksiteMapper) {
        this.worksiteService = worksiteService;
        this.worksiteMapper = worksiteMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Return the list of all worksites by city ascending.")
    public ResponseEntity<List<WorksiteDto>> getAll() {
        log.info("Retrieving worksites...");
        return ResponseEntity.ok(
                this.worksiteService.getAll().stream()
                        .map(this.worksiteMapper::mapToDto)
                        .toList()
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Trying to retrieve a worksite from the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return an worksite found the given ID"),
            @ApiResponse(responseCode = "404", description = "No worksite found the given ID")
    })
    public ResponseEntity<WorksiteDto> getById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(
                    this.worksiteMapper
                            .mapToDto(this.worksiteService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    @Operation(summary = "Create a worksite")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<WorksiteDto> createWorksite(@RequestBody final WorksiteDto worksiteDto) {
        log.debug("Attempting to create worksite with name {}", worksiteDto.getName());

        WorksiteDto worksiteDtoResponse =
                this.worksiteMapper.mapToDto(
                        this.worksiteService.createWorksite(
                                this.worksiteMapper.mapToModel(worksiteDto)
                        ));

        return ResponseEntity
                .created(URI.create("/v1/worksites/" + worksiteDtoResponse.getId()))
                .body(worksiteDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a worksite for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No worksite found the given ID"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the worksite for the given ID")
    })
    public ResponseEntity<Void> deleteWorksite(@PathVariable final Integer id) {
        log.debug("Attemtping to delete a worksite with id {}", id);
        try {
            this.worksiteService.deleteWorksite(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a worksite")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Void> updateWorksite(@PathVariable final Integer id, @RequestBody WorksiteDto worksiteDto) {
        try {
            log.debug("Updating worksite {}", worksiteDto.getId());
            worksiteDto.setId(id);
            this.worksiteService.updateWorksite(worksiteMapper.mapToModel(worksiteDto));
            log.debug("Successfully updated worksite {}", worksiteDto.getId());

            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }


}

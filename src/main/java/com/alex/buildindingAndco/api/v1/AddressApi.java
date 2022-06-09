package com.alex.buildindingAndco.api.v1;

import com.alex.buildindingAndco.api.dto.AddressDto;
import com.alex.buildindingAndco.exception.NotAllowedToDeleteException;
import com.alex.buildindingAndco.exception.UnknownResourceException;
import com.alex.buildindingAndco.mapper.AddressMapper;
import com.alex.buildindingAndco.service.AddressService;
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
@RequestMapping("/v1/addresses")
public class AddressApi {

    Logger log = LoggerFactory.getLogger(AddressApi.class);

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressApi(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(
            summary = "Return the list of all addresses by city ascending.")
    public ResponseEntity<List<AddressDto>> getAll() {
        log.info("Retrieving addresses...");

        return ResponseEntity.ok(
                this.addressService.getAll().stream()
                        .map(this.addressMapper::mapToDto)
                        .toList()
        );
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Trying to retrieve a address from the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return an address found the given ID"),
            @ApiResponse(responseCode = "404", description = "No address found the given ID")
    })
    public ResponseEntity<AddressDto> getById(@PathVariable final Integer id) {
        try {
            return ResponseEntity.ok(
                    this.addressMapper
                            .mapToDto(this.addressService.getById(id)));
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    @Operation(summary = "Create a address")
    @ApiResponse(responseCode = "201", description = "Created")
    public ResponseEntity<AddressDto> createAddress(@RequestBody final AddressDto addressDto) {
        log.debug("Attempting to create Address with city {}", addressDto.getCity());

        AddressDto addressDtoResponse =
                this.addressMapper.mapToDto(
                        this.addressService.createAddress(
                                this.addressMapper.mapToModel(addressDto)
                        ));

        return ResponseEntity
                .created(URI.create("/v1/addresss/" + addressDtoResponse.getId()))
                .body(addressDtoResponse);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a address for the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "No address found the given ID"),
            @ApiResponse(responseCode = "403", description = "Cannot delete the address for the given ID")
    })
    public ResponseEntity<Void> deleteAddress(@PathVariable final Integer id) {
        log.debug("Attemtping to delete a address with id {}", id);
        try {
            this.addressService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        } catch (NotAllowedToDeleteException ex) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ex.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a address")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content")
    })
    public ResponseEntity<Void> updateAddress(@PathVariable final Integer id, @RequestBody AddressDto addressDto) {
        try {
            log.debug("Updating address {}", addressDto.getId());
            addressDto.setId(id);
            this.addressService.updateAddress(addressMapper.mapToModel(addressDto));
            log.debug("Successfully updated address {}", addressDto.getId());

            return ResponseEntity.noContent().build();
        } catch (UnknownResourceException ure) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ure.getMessage());
        }
    }


}

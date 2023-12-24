package com.omerfaruksahin.flight.rest;

import com.omerfaruksahin.flight.model.AirportDTO;
import com.omerfaruksahin.flight.service.AirportService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/airports")
public class AirportResource {

    private final AirportService airportService;

    @GetMapping("/getAllAirports")
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        return ResponseEntity.ok(airportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(airportService.get(id));
    }

    @PostMapping("/createAirport")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAirport(@RequestBody @Valid final AirportDTO airportDTO) {
        final Long createdId = airportService.create(airportDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAirport(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AirportDTO airportDTO) {
        airportService.update(id, airportDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAirport(@PathVariable(name = "id") final Long id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

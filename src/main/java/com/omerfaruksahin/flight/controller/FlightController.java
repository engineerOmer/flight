package com.omerfaruksahin.flight.controller;

import com.omerfaruksahin.flight.model.FlightDTO;
import com.omerfaruksahin.flight.service.FlightService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/api/flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/getAllFlights")
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.findAll());
    }
    @GetMapping("/departure/{departureAirport}")
    public ResponseEntity<List<FlightDTO>> searchByDeparture(@PathVariable(name = "departureAirport")final Long departureAirport){
        return ResponseEntity.ok(flightService.sortByVariable(departureAirport));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(flightService.get(id));
    }

    @PostMapping("/createFlight")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFlight(@RequestBody @Valid final FlightDTO flightDTO) {
        final Long createdId = flightService.create(flightDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFlight(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FlightDTO flightDTO) {
        flightService.update(id, flightDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<String> deleteFlight(@PathVariable(name = "id") final Long id) {
        return new ResponseEntity<>(flightService.delete(id), HttpStatus.OK);

    }

}

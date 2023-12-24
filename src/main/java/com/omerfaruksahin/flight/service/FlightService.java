package com.omerfaruksahin.flight.service;

import com.omerfaruksahin.flight.domain.Airport;
import com.omerfaruksahin.flight.domain.Flight;
import com.omerfaruksahin.flight.model.FlightDTO;
import com.omerfaruksahin.flight.repos.AirportRepository;
import com.omerfaruksahin.flight.repos.FlightRepository;
import com.omerfaruksahin.flight.util.NotFoundException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public List<FlightDTO> findAll() {
        final List<Flight> flights = flightRepository.findAll(Sort.by("id"));
        return flights.stream()
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .toList();
    }

    public List<FlightDTO> sortByVariable(final Long departureAirport){
        final List<Flight> flights = flightRepository.findAll(Sort.by("departureAirport"));
        return flights.stream()
                .map(flight->mapToDTO(flight,new FlightDTO()))
                .toList();
    }



    public FlightDTO get(final Long id) {
        return flightRepository.findById(id)
                .map(flight -> mapToDTO(flight, new FlightDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FlightDTO flightDTO) {
        final Flight flight = new Flight();
        mapToEntity(flightDTO, flight);
        return flightRepository.save(flight).getId();
    }

    public void update(final Long id, final FlightDTO flightDTO) {
        final Flight flight = flightRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(flightDTO, flight);
        flightRepository.save(flight);
    }

    public String delete(final Long id) {
        flightRepository.deleteById(id);
        return String.format("Uçuş iptal edildi",id);
    }

    private FlightDTO mapToDTO(final Flight flight, final FlightDTO flightDTO) {
        flightDTO.setId(flight.getId());
        flightDTO.setPrice(flight.getPrice());
        flightDTO.setDepartureDate(flight.getDepartureDate());
        flightDTO.setArrivalDate(flight.getArrivalDate());
        flightDTO.setArrivalAirport(flight.getArrivalAirport() == null ? null : flight.getArrivalAirport().getId());
        flightDTO.setDepartureAirport(flight.getDepartureAirport() == null ? null : flight.getDepartureAirport().getId());
        return flightDTO;
    }

    private Flight mapToEntity(final FlightDTO flightDTO, final Flight flight) {
        flight.setPrice(flightDTO.getPrice());
        flight.setDepartureDate(flightDTO.getDepartureDate());
        flight.setArrivalDate(flightDTO.getArrivalDate());
        final Airport arrivalAirport = flightDTO.getArrivalAirport() == null ? null : airportRepository.findById(flightDTO.getArrivalAirport())
                .orElseThrow(() -> new NotFoundException("arrivalAirport not found"));
        flight.setArrivalAirport(arrivalAirport);
        final Airport departureAirport = flightDTO.getDepartureAirport() == null ? null : airportRepository.findById(flightDTO.getDepartureAirport())
                .orElseThrow(() -> new NotFoundException("departureAirport not found"));
        flight.setDepartureAirport(departureAirport);
        return flight;
    }

}

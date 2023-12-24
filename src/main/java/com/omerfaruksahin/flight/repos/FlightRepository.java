package com.omerfaruksahin.flight.repos;

import com.omerfaruksahin.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlightRepository extends JpaRepository<Flight, Long> {
}

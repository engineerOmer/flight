package com.omerfaruksahin.flight.repos;

import com.omerfaruksahin.flight.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByCityIgnoreCase(String city);

}

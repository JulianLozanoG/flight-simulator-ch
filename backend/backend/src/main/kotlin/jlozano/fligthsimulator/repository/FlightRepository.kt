package jlozano.fligthsimulator.repository

import jlozano.fligthsimulator.domain.Flight
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FlightRepository : JpaRepository<Flight, UUID> {
}
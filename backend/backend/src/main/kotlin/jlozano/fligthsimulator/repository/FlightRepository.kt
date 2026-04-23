package jlozano.fligthsimulator.repository

import jlozano.fligthsimulator.domain.Flight
import jlozano.fligthsimulator.domain.FlightMetric
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FlightRepository : JpaRepository<Flight, UUID> {
    fun findAllByFlightIdOrderByTimestampAsc(flightId: UUID): List<FlightMetric>
    fun findTopByFlightIdOrderByTimestampDesc(flightId: UUID): FlightMetric?
}
package jlozano.fligthsimulator.simulation

import jlozano.fligthsimulator.repository.FlightRepository
import jlozano.fligthsimulator.service.FlightServiceImpl
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class FlightSimulationScheduler(
    private val flightRepository: FlightRepository,
    private val flightServiceImpl: FlightServiceImpl
) {

    @Scheduled(fixedDelayString = "\${simulation.schedulerIntervalMillis:2000}")
    fun advanceActiveFlights() {
        val activeFlightIds: List<UUID> = flightRepository.findAll()
            .filter { it.status.name == "ACTIVE" }
            .mapNotNull { it.id }

        activeFlightIds.forEach { flightId ->
            flightServiceImpl.advanceFlight(flightId)
        }
    }
}
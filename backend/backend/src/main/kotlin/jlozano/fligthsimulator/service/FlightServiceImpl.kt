package jlozano.fligthsimulator.service

import jlozano.fligthsimulator.config.SimulationProperties
import jlozano.fligthsimulator.domain.Flight
import jlozano.fligthsimulator.domain.FlightMetric
import jlozano.fligthsimulator.domain.FlightPhase
import jlozano.fligthsimulator.domain.FlightStatus
import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightResponse
import jlozano.fligthsimulator.exception.FlightNotFoundException
import jlozano.fligthsimulator.repository.FlightMetricRepository
import jlozano.fligthsimulator.repository.FlightRepository
import jlozano.fligthsimulator.simulation.FlightSimulationEngine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class FlightServiceImpl(
    private val flightRepository: FlightRepository,
    private val metricRepository: FlightMetricRepository,
    private val simulationProperties: SimulationProperties
) : FlightService {

    private val engine by lazy { FlightSimulationEngine(simulationProperties) }

    @Transactional
    override fun createFlight(request: CreateFlightRequest): FlightResponse {
        val flight = flightRepository.save(
            Flight(
                origin = request.origin,
                destination = request.destination,
                status = FlightStatus.ACTIVE,
                currentPhase = FlightPhase.BOARDING,
                progress = 0.0
            )
        )

        val metric = engine.generateMetric(flight, 0.0)
        val savedMetric = metricRepository.save(metric)

        return toResponse(flight, savedMetric)
    }

    override fun listFlights(): List<FlightResponse> {
        return flightRepository.findAll().map { flight ->
            val latestMetric = metricRepository.findTopByFlightIdOrderByTimestampDesc(flight.id!!)
            toResponse(flight, latestMetric)
        }
    }

    override fun getFlight(id: UUID): FlightResponse {
        val flight = flightRepository.findById(id)
            .orElseThrow { FlightNotFoundException("Flight with id $id not found") }

        val latestMetric = metricRepository.findTopByFlightIdOrderByTimestampDesc(id)
        return toResponse(flight, latestMetric)
    }

    override fun getFlightHistory(id: UUID): List<FlightMetricResponse> {
        flightRepository.findById(id)
            .orElseThrow { FlightNotFoundException("Flight with id $id not found") }

        return metricRepository.findAllByFlightIdOrderByTimestampAsc(id)
            .map { toMetricResponse(it) }
    }

    @Transactional
    fun advanceFlight(id: UUID): FlightResponse {
        val flight = flightRepository.findById(id)
            .orElseThrow { FlightNotFoundException("Flight with id $id not found") }

        if (flight.status != FlightStatus.ACTIVE) {
            val latestMetric = metricRepository.findTopByFlightIdOrderByTimestampDesc(id)
            return toResponse(flight, latestMetric)
        }

        val nextProgress = engine.nextProgress(flight.progress)
        val nextMetric = engine.generateMetric(flight, nextProgress)
        val savedMetric = metricRepository.save(nextMetric)

        flight.progress = nextProgress
        flight.currentPhase = savedMetric.phase
        flight.status = if (nextProgress >= 1.0) FlightStatus.COMPLETED else FlightStatus.ACTIVE
        flight.updatedAt = savedMetric.timestamp
        flightRepository.save(flight)

        return toResponse(flight, savedMetric)
    }

    private fun toResponse(flight: Flight, latestMetric: FlightMetric?): FlightResponse {
        return FlightResponse(
            id = flight.id!!,
            origin = flight.origin,
            destination = flight.destination,
            status = flight.status,
            currentPhase = flight.currentPhase,
            progress = flight.progress,
            route = engine.routeForFlight(flight.origin, flight.destination),
            latestMetric = latestMetric?.let { toMetricResponse(it) }
        )
    }

    private fun toMetricResponse(metric: FlightMetric): FlightMetricResponse {
        return FlightMetricResponse(
            id = metric.id,
            flightId = metric.flightId,
            timestamp = metric.timestamp,
            phase = metric.phase,
            altitudeFeet = metric.altitudeFeet,
            airspeedKnots = metric.airspeedKnots,
            headingDegrees = metric.headingDegrees,
            latitude = metric.latitude,
            longitude = metric.longitude,
            fuelRemaining = metric.fuelRemaining,
            outsideAirTemperatureC = metric.outsideAirTemperatureC,
            estimatedTimeToArrivalMinutes = metric.estimatedTimeToArrivalMinutes,
            progress = metric.progress
        )
    }
}
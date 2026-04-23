package jlozano.fligthsimulator.service

import jakarta.transaction.Transactional
import jlozano.fligthsimulator.domain.Flight
import jlozano.fligthsimulator.domain.FlightMetric
import jlozano.fligthsimulator.domain.FlightPhase
import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightStatusResponse
import jlozano.fligthsimulator.exception.FlightNotFoundException
import jlozano.fligthsimulator.repository.FlightMetricRepository
import jlozano.fligthsimulator.repository.FlightRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class FlightServiceImpl(
    private val flightRepository: FlightRepository,
    private val metricRepository: FlightMetricRepository
) : FlightService {

    @Transactional
    override fun createFlight(request: CreateFlightRequest): FlightStatusResponse {
        val flight = flightRepository.save(
            Flight(
                origin = request.origin,
                destination = request.destination
            )
        )

        return toStatusResponse(flight, null)
    }

    override fun listFlights(): List<FlightStatusResponse> {
        return flightRepository.findAll().map { flight ->
            val latestMetric = metricRepository.findTopByFlightIdOrderByTimestampDesc(flight.id!!)
            toStatusResponse(flight, latestMetric?.let { toMetricResponse(it) })
        }
    }

    override fun getFlight(id: UUID): FlightStatusResponse {
        val flight = flightRepository.findById(id)
            .orElseThrow { FlightNotFoundException("Flight with id $id not found") }

        val latestMetric = metricRepository.findTopByFlightIdOrderByTimestampDesc(id)
        return toStatusResponse(flight, latestMetric?.let { toMetricResponse(it) })
    }

    override fun getFlightHistory(id: UUID): List<FlightMetricResponse> {
        flightRepository.findById(id)
            .orElseThrow { FlightNotFoundException("Flight with id $id not found") }

        return metricRepository.findAllByFlightIdOrderByTimestampAsc(id)
            .map { toMetricResponse(it) }
    }

    @Transactional
    fun saveMetric(
        flightId: UUID,
        phase: FlightPhase,
        altitudeFeet: Int,
        airspeedKnots: Int,
        headingDegrees: Int,
        latitude: Double,
        longitude: Double,
        fuelRemaining: Double,
        outsideAirTemperatureC: Double,
        estimatedTimeToArrivalMinutes: Long
    ): FlightMetricResponse {
        val metric = metricRepository.save(
            FlightMetric(
                flightId = flightId,
                timestamp = Instant.now(),
                phase = phase,
                altitudeFeet = altitudeFeet,
                airspeedKnots = airspeedKnots,
                headingDegrees = headingDegrees,
                latitude = latitude,
                longitude = longitude,
                fuelRemaining = fuelRemaining,
                outsideAirTemperatureC = outsideAirTemperatureC,
                estimatedTimeToArrivalMinutes = estimatedTimeToArrivalMinutes
            )
        )

        val flight = flightRepository.findById(flightId)
            .orElseThrow { FlightNotFoundException("Flight with id $flightId not found") }

        flight.currentPhase = phase
        flight.updatedAt = Instant.now()
        flightRepository.save(flight)

        return toMetricResponse(metric)
    }

    private fun toStatusResponse(
        flight: Flight,
        latestMetric: FlightMetricResponse?
    ): FlightStatusResponse {
        return FlightStatusResponse(
            id = flight.id!!,
            origin = flight.origin,
            destination = flight.destination,
            status = flight.status,
            currentPhase = flight.currentPhase,
            latestMetric = latestMetric
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
            estimatedTimeToArrivalMinutes = metric.estimatedTimeToArrivalMinutes
        )
    }
}
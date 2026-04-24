package jlozano.fligthsimulator.dto

import jlozano.fligthsimulator.domain.FlightPhase
import java.time.Instant
import java.util.UUID

data class FlightMetricResponse(
    val id: UUID?,
    val flightId: UUID,
    val timestamp: Instant,
    val phase: FlightPhase,
    val altitudeFeet: Int,
    val airspeedKnots: Int,
    val headingDegrees: Int,
    val latitude: Double,
    val longitude: Double,
    val fuelRemaining: Double,
    val outsideAirTemperatureC: Double,
    val estimatedTimeToArrivalMinutes: Long,
    val progress: Double
)
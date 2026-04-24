package jlozano.fligthsimulator.simulation

import jlozano.fligthsimulator.config.SimulationProperties
import jlozano.fligthsimulator.domain.Flight
import jlozano.fligthsimulator.domain.FlightMetric
import jlozano.fligthsimulator.domain.FlightPhase
import jlozano.fligthsimulator.dto.RoutePointResponse
import java.time.Instant

class FlightSimulationEngine(
    private val properties: SimulationProperties
) {
    fun generateMetric(flight: Flight, progress: Double): FlightMetric {
        val phase = phaseFromProgress(progress)
        val altitude = altitudeFor(phase, progress)
        val airspeed = airspeedFor(phase)
        val heading = (30 + (progress * 25)).toInt()
        val fuel = (100 - progress * 55).coerceAtLeast(0.0)
        val temperature = temperatureFor(phase)
        val eta = ((1 - progress) * totalFlightMinutes()).toLong().coerceAtLeast(0)
        val position = positionFor(progress)

        return FlightMetric(
            flightId = flight.id!!,
            timestamp = Instant.now(),
            phase = phase,
            altitudeFeet = altitude,
            airspeedKnots = airspeed,
            headingDegrees = heading,
            latitude = position.y,
            longitude = position.x,
            fuelRemaining = fuel,
            outsideAirTemperatureC = temperature,
            estimatedTimeToArrivalMinutes = eta,
            progress = progress
        )
    }

    fun routeForFlight(): List<RoutePointResponse> {
        return listOf(
            RoutePointResponse(80.0, 320.0),
            RoutePointResponse(160.0, 240.0),
            RoutePointResponse(260.0, 280.0),
            RoutePointResponse(380.0, 170.0),
            RoutePointResponse(520.0, 210.0),
            RoutePointResponse(650.0, 130.0),
            RoutePointResponse(780.0, 180.0)
        )
    }

    private fun phaseFromProgress(progress: Double): FlightPhase {
        return when {
            progress < 0.1 -> FlightPhase.BOARDING
            progress < 0.15 -> FlightPhase.TAXI_OUT
            progress < 0.25 -> FlightPhase.TAKEOFF_CLIMB
            progress < 0.8 -> FlightPhase.CRUISE
            progress < 0.9 -> FlightPhase.DESCENT
            progress < 0.95 -> FlightPhase.LANDING
            progress < 1.0 -> FlightPhase.TAXI_IN
            else -> FlightPhase.COMPLETED
        }
    }

    private fun altitudeFor(phase: FlightPhase, progress: Double): Int {
        return when (phase) {
            FlightPhase.BOARDING -> 0
            FlightPhase.TAXI_OUT -> 0
            FlightPhase.TAKEOFF_CLIMB -> (10000 + progress * 80000).toInt()
            FlightPhase.CRUISE -> 32000
            FlightPhase.DESCENT -> (32000 - (progress - 0.8) * 160000).toInt().coerceAtLeast(0)
            FlightPhase.LANDING -> 3000
            FlightPhase.TAXI_IN -> 0
            FlightPhase.COMPLETED -> 0
        }
    }

    private fun airspeedFor(phase: FlightPhase): Int {
        return when (phase) {
            FlightPhase.BOARDING -> 0
            FlightPhase.TAXI_OUT -> 20
            FlightPhase.TAKEOFF_CLIMB -> 180
            FlightPhase.CRUISE -> 430
            FlightPhase.DESCENT -> 290
            FlightPhase.LANDING -> 160
            FlightPhase.TAXI_IN -> 15
            FlightPhase.COMPLETED -> 0
        }
    }

    private fun temperatureFor(phase: FlightPhase): Double {
        return when (phase) {
            FlightPhase.CRUISE -> -45.0
            FlightPhase.DESCENT -> -20.0
            FlightPhase.LANDING -> 5.0
            else -> 12.0
        }
    }

    private fun positionFor(progress: Double): RoutePointResponse {
        val route = routeForFlight()
        if (route.isEmpty()) return RoutePointResponse(0.0, 0.0)
        if (route.size == 1) return route.first()

        val clamped = progress.coerceIn(0.0, 1.0)
        val totalSegments = route.size - 1
        val scaled = clamped * totalSegments
        val segmentIndex = scaled.toInt().coerceAtMost(totalSegments - 1)
        val localT = scaled - segmentIndex

        val start = route[segmentIndex]
        val end = route[segmentIndex + 1]

        return RoutePointResponse(
            x = start.x + (end.x - start.x) * localT,
            y = start.y + (end.y - start.y) * localT
        )
    }

    private fun totalFlightMinutes(): Int {
        return properties.phases.boardingMinutes +
                properties.phases.taxiOutMinutes +
                properties.phases.takeoffClimbMinutes +
                properties.phases.cruiseMinutes +
                properties.phases.descentMinutes +
                properties.phases.landingMinutes +
                properties.phases.taxiInMinutes
    }
}
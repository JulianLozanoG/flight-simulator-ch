package jlozano.fligthsimulator.simulation

import jlozano.fligthsimulator.config.SimulationProperties
import jlozano.fligthsimulator.domain.Flight
import jlozano.fligthsimulator.domain.FlightMetric
import java.util.Properties

class FlightSimulationEngine(
    private val properties: SimulationProperties
) {
    fun generateMetric(flight: Flight, elapsedSimMinutes: Long): FlightMetric {
        TODO("Calcular fase")
    }
}
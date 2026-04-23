package jlozano.fligthsimulator.dto

import jlozano.fligthsimulator.domain.FlightPhase
import jlozano.fligthsimulator.domain.FlightStatus
import java.util.UUID

data class FlightStatusResponse(
    val id: UUID,
    val origin: String,
    val destination: String,
    val status: FlightStatus,
    val currentPhase: FlightPhase,
    val latestMetric: FlightMetricResponse?
)

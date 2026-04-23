package jlozano.fligthsimulator.service

import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightStatusResponse
import java.util.UUID

interface FlightService {
    fun createFlight(request: CreateFlightRequest): FlightStatusResponse
    fun listFlights(): List<FlightStatusResponse>
    fun getFlight(id: UUID): FlightStatusResponse
    fun getFlightHistory(id: UUID): List<FlightMetricResponse>
}
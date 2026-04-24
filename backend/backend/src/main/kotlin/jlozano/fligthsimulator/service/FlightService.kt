package jlozano.fligthsimulator.service

import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightResponse
import java.util.UUID

interface FlightService {
    fun createFlight(request: CreateFlightRequest): FlightResponse
    fun listFlights(): List<FlightResponse>
    fun getFlight(id: UUID): FlightResponse
    fun getFlightHistory(id: UUID): List<FlightMetricResponse>
}
package jlozano.fligthsimulator.service

import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightStatusResponse
import jlozano.fligthsimulator.repository.FlightMetricRepository
import jlozano.fligthsimulator.repository.FlightRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FlightServiceImpl(
    private val flightRepository: FlightRepository,
    private val metricRepository: FlightMetricRepository
) : FlightService {

    override fun createFlight(request: CreateFlightRequest): FlightStatusResponse {
        TODO("Crear vuelo y arrancar simulación")
    }

    override fun listFlights(): List<FlightStatusResponse> {
        TODO("Listar vuelos")
    }

    override fun getFlight(id: UUID): FlightStatusResponse {
        TODO("Obtener vuelo por id")
    }

    override fun getFlightHistory(id: UUID): List<FlightMetricResponse> {
        TODO("Obtener historial")
    }
}
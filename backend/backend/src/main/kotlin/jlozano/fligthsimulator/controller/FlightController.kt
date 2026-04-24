package jlozano.fligthsimulator.controller

import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightResponse
import jlozano.fligthsimulator.service.FlightService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightService: FlightService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateFlightRequest): FlightResponse {
        return flightService.createFlight(request)
    }

    @GetMapping
    fun list(): List<FlightResponse> {
        return flightService.listFlights()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): FlightResponse {
        return flightService.getFlight(id)
    }

    @GetMapping("/{id}/history")
    fun history(@PathVariable id: UUID): List<FlightMetricResponse> {
        return flightService.getFlightHistory(id)
    }
}
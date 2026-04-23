package jlozano.fligthsimulator.controller

import jakarta.validation.Valid
import jlozano.fligthsimulator.dto.CreateFlightRequest
import jlozano.fligthsimulator.dto.FlightMetricResponse
import jlozano.fligthsimulator.dto.FlightStatusResponse
import jlozano.fligthsimulator.service.FlightService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/flights")
class FlightController(
    private val flightService: FlightService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateFlightRequest): FlightStatusResponse {
        return flightService.createFlight(request)
    }

    @GetMapping
    fun list(): List<FlightStatusResponse> {
        return flightService.listFlights()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): FlightStatusResponse {
        return flightService.getFlight(id)
    }

    @GetMapping("/{id}/history")
    fun history(@PathVariable id: UUID): List<FlightMetricResponse> {
        return flightService.getFlightHistory(id)
    }
}
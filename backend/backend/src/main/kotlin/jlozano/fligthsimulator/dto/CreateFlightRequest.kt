package jlozano.fligthsimulator.dto

import jakarta.validation.constraints.NotBlank

data class CreateFlightRequest(
    @field:NotBlank
    val origin: String,

    @field:NotBlank
    val destination: String
)
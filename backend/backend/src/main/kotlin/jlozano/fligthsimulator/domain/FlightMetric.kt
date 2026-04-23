package jlozano.fligthsimulator.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "flight_metrics")
data class FlightMetric(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false)
    val flightId: UUID,

    @Column(nullable = false)
    val timestamp: Instant,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val phase: FlightPhase,

    @Column(nullable = false)
    val altitudeFeet: Int,

    @Column(nullable = false)
    val airspeedKnots: Int,

    @Column(nullable = false)
    val headingDegrees: Int,

    @Column(nullable = false)
    val latitude: Double,

    @Column(nullable = false)
    val longitude: Double,

    @Column(nullable = false)
    val fuelRemaining: Double,

    @Column(nullable = false)
    val outsideAirTemperatureC: Double,

    @Column(nullable = false)
    val estimatedTimeToArrivalMinutes: Long
)

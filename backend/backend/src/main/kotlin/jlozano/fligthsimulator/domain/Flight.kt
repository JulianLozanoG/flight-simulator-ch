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
@Table(name = "flights")
data class Flight(

    @Id
    @GeneratedValue
    var id: UUID? = null,

    @Column(nullable = false)
    val origin: String,

    @Column(nullable = false)
    val destination: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: FlightStatus = FlightStatus.ACTIVE,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var currentPhase: FlightPhase = FlightPhase.BOARDING,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    var updatedAt: Instant? = null
)
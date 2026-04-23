package jlozano.fligthsimulator.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "simulation")
data class SimulationProperties(
    val acceleratedMinutesPerSecond: Int,
    val defaultRoute: DefaultRoute,
    val phases: Phases
) {
    data class DefaultRoute(
        val origin: String,
        val destination: String
    )

    data class Phases(
        val boardingMinutes: Int,
        val taxiOutMinutes: Int,
        val takeoffClimbMinutes: Int,
        val cruiseMinutes: Int,
        val descentMinutes: Int,
        val landingMinutes: Int,
        val taxiInMinutes: Int
    )
}

package jlozano.fligthsimulator.domain

enum class FlightPhase {
    BOARDING,
    TAXI_OUT,
    TAKEOFF_CLIMB,
    CRUISE,
    DESCENT,
    LANDING,
    TAXI_IN,
    COMPLETED
}
import { FlightSimulation } from "./types";

export const mockFlight: FlightSimulation = {
  id: "flight-001",
  origin: "MDE",
  destination: "BOG",
  status: "ACTIVE",
  currentPhase: "CRUISE",
  progress: 0.52,
  route: [
    { x: 80, y: 320 },
    { x: 160, y: 240 },
    { x: 260, y: 280 },
    { x: 380, y: 170 },
    { x: 520, y: 210 },
    { x: 650, y: 130 },
    { x: 780, y: 180 },
  ],
  latestMetric: {
    phase: "CRUISE",
    altitudeFeet: 32000,
    airspeedKnots: 430,
    headingDegrees: 34,
    latitude: 6.25,
    longitude: -75.57,
    fuelRemaining: 68,
    outsideAirTemperatureC: -45,
    estimatedTimeToArrivalMinutes: 58,
    progress: 0.52,
  },
};
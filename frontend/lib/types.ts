export type FlightPhase =
  | "BOARDING"
  | "TAXI_OUT"
  | "TAKEOFF_CLIMB"
  | "CRUISE"
  | "DESCENT"
  | "LANDING"
  | "TAXI_IN"
  | "COMPLETED";

export type FlightStatus = "ACTIVE" | "COMPLETED" | "FAILED";

export interface RoutePoint {
  x: number;
  y: number;
}

export interface FlightMetric {
  id?: string;
  flightId?: string;
  timestamp?: string;
  phase: FlightPhase;
  altitudeFeet: number;
  airspeedKnots: number;
  headingDegrees: number;
  latitude: number;
  longitude: number;
  fuelRemaining: number;
  outsideAirTemperatureC: number;
  estimatedTimeToArrivalMinutes: number;
  progress: number;
}

export interface FlightResponse {
  id: string;
  origin: string;
  destination: string;
  status: FlightStatus;
  currentPhase: FlightPhase;
  progress: number;
  route: RoutePoint[];
  latestMetric: FlightMetric | null;
}
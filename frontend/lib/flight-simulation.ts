import { FlightMetric, FlightPhase } from "./types";

export function getPhaseFromProgress(progress: number): FlightPhase {
  if (progress < 0.1) return "BOARDING";
  if (progress < 0.15) return "TAXI_OUT";
  if (progress < 0.25) return "TAKEOFF_CLIMB";
  if (progress < 0.8) return "CRUISE";
  if (progress < 0.9) return "DESCENT";
  if (progress < 0.95) return "LANDING";
  if (progress < 1) return "TAXI_IN";
  return "COMPLETED";
}

export function getMetricFromProgress(progress: number): FlightMetric {
  const phase = getPhaseFromProgress(progress);

  const altitudeFeet =
    phase === "BOARDING" || phase === "TAXI_OUT"
      ? 0
      : phase === "TAKEOFF_CLIMB"
        ? Math.round(10000 + progress * 80000)
        : phase === "CRUISE"
          ? 32000
          : phase === "DESCENT"
            ? Math.round(32000 - (progress - 0.8) * 160000)
            : phase === "LANDING"
              ? 3000
              : 0;

  const airspeedKnots =
    phase === "BOARDING"
      ? 0
      : phase === "TAXI_OUT"
        ? 20
        : phase === "TAKEOFF_CLIMB"
          ? 180
          : phase === "CRUISE"
            ? 430
            : phase === "DESCENT"
              ? 290
              : phase === "LANDING"
                ? 160
                : 15;

  const headingDegrees = Math.round(30 + progress * 20);
  const fuelRemaining = Math.max(0, Math.round(100 - progress * 55));
  const outsideAirTemperatureC =
    phase === "CRUISE" ? -45 : phase === "DESCENT" ? -20 : 12;

  const estimatedTimeToArrivalMinutes = Math.max(0, Math.round((1 - progress) * 120));

  return {
    phase,
    altitudeFeet,
    airspeedKnots,
    headingDegrees,
    latitude: 6.25 + progress * 0.5,
    longitude: -75.57 + progress * 1.2,
    fuelRemaining,
    outsideAirTemperatureC,
    estimatedTimeToArrivalMinutes,
    progress,
  };
}
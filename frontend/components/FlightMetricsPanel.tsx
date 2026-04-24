import { FlightSimulation } from "@/lib/types";

type FlightMetricsPanelProps = {
  flight: FlightSimulation;
};

export function FlightMetricsPanel({ flight }: FlightMetricsPanelProps) {
  const metric = flight.latestMetric;

  return (
    <aside className="rounded-3xl border border-white/10 bg-white/5 p-5 shadow-lg backdrop-blur">
      <h2 className="mb-4 text-lg font-semibold">Flight Metrics</h2>

      <div className="space-y-4">
        <Metric label="Current Phase" value={metric.phase} />
        <Metric label="Altitude" value={`${metric.altitudeFeet.toLocaleString()} ft`} />
        <Metric label="Airspeed" value={`${metric.airspeedKnots} kt`} />
        <Metric label="Heading" value={`${metric.headingDegrees}°`} />
        <Metric label="Fuel Remaining" value={`${metric.fuelRemaining}%`} />
        <Metric label="Outside Temp" value={`${metric.outsideAirTemperatureC} °C`} />
        <Metric label="ETA" value={`${metric.estimatedTimeToArrivalMinutes} min`} />
      </div>
    </aside>
  );
}

function Metric({ label, value }: { label: string; value: string }) {
  return (
    <div className="rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3">
      <p className="text-xs uppercase tracking-widest text-slate-400">{label}</p>
      <p className="mt-1 text-lg font-semibold text-white">{value}</p>
    </div>
  );
}
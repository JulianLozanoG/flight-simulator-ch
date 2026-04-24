"use client";

import { useEffect, useState } from "react";
import { FlightMap } from "@/components/FlightMap";
import { FlightMetricsPanel } from "@/components/FlightMetricsPanel";
import { FlightTimeline } from "@/components/FlightTimeline";
import { createFlight, getFlight } from "@/lib/api";
import { FlightResponse } from "@/lib/types";

const EMPTY_FLIGHT: FlightResponse = {
  id: "",
  origin: "",
  destination: "",
  status: "ACTIVE",
  currentPhase: "BOARDING",
  progress: 0,
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
    phase: "BOARDING",
    altitudeFeet: 0,
    airspeedKnots: 0,
    headingDegrees: 0,
    latitude: 0,
    longitude: 0,
    fuelRemaining: 100,
    outsideAirTemperatureC: 12,
    estimatedTimeToArrivalMinutes: 0,
    progress: 0,
  },
};

export default function Home() {
  const [flight, setFlight] = useState<FlightResponse>(EMPTY_FLIGHT);
  const [activeFlightId, setActiveFlightId] = useState<string | null>(null);
  const [origin, setOrigin] = useState("MDE");
  const [destination, setDestination] = useState("BOG");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleStartSimulation() {
    try {
      setLoading(true);
      setError(null);

      const created = await createFlight(origin, destination);
      setActiveFlightId(created.id);

      const current = await getFlight(created.id);
      setFlight(current);
    } catch (err) {
      setError(err instanceof Error ? err.message : "Failed to start simulation");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (!activeFlightId) return;

    const interval = setInterval(async () => {
      try {
        const updated = await getFlight(activeFlightId);
        setFlight(updated);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Failed to refresh flight");
      }
    }, 2000);

    return () => clearInterval(interval);
  }, [activeFlightId]);

  return (
    <main className="min-h-screen bg-gradient-to-b from-slate-950 via-slate-900 to-slate-800 text-white">
      <div className="mx-auto flex max-w-7xl flex-col gap-6 px-6 py-8">
        <header className="rounded-2xl border border-white/10 bg-white/5 px-6 py-4 shadow-lg backdrop-blur">
          <div className="flex flex-col gap-4">
            <div className="flex flex-col gap-2 md:flex-row md:items-center md:justify-between">
              <div>
                <p className="text-sm uppercase tracking-[0.3em] text-cyan-300">
                  Flight Status Simulator
                </p>
                <h1 className="text-2xl font-semibold">
                  {flight.origin || "Origin"} → {flight.destination || "Destination"}
                </h1>
              </div>

              <div className="text-sm text-slate-300">
                Status: <span className="font-semibold text-emerald-400">{flight.status}</span>
              </div>
            </div>

            <div className="grid gap-3 md:grid-cols-[1fr_1fr_auto]">
              <input
                value={origin}
                onChange={(e) => setOrigin(e.target.value.toUpperCase())}
                placeholder="Origin"
                className="rounded-xl border border-white/10 bg-slate-950/60 px-4 py-3 text-white outline-none placeholder:text-slate-500"
              />
              <input
                value={destination}
                onChange={(e) => setDestination(e.target.value.toUpperCase())}
                placeholder="Destination"
                className="rounded-xl border border-white/10 bg-slate-950/60 px-4 py-3 text-white outline-none placeholder:text-slate-500"
              />
              <button
                onClick={handleStartSimulation}
                disabled={loading || !origin || !destination}
                className="rounded-xl bg-cyan-500 px-5 py-3 text-sm font-semibold text-slate-950 transition hover:bg-cyan-400 disabled:cursor-not-allowed disabled:opacity-60"
              >
                {loading ? "Starting..." : "Start Simulation"}
              </button>
            </div>
          </div>

          {activeFlightId && (
            <p className="mt-3 text-xs text-slate-400">
              Active Flight ID: <span className="text-slate-200">{activeFlightId}</span>
            </p>
          )}

          {error && (
            <p className="mt-3 rounded-xl border border-red-500/30 bg-red-500/10 px-4 py-2 text-sm text-red-300">
              {error}
            </p>
          )}
        </header>

        <section className="grid gap-6 lg:grid-cols-[2fr_1fr]">
          <FlightMap flight={flight} />
          <FlightMetricsPanel flight={flight} />
        </section>

        <FlightTimeline currentPhase={flight.currentPhase} />
      </div>
    </main>
  );
}
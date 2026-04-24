import { FlightMetric, FlightSimulation } from "./types";

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080";

export async function createFlight(): Promise<{ id: string }> {
  const response = await fetch(`${API_BASE_URL}/flights`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      origin: "MDE",
      destination: "BOG",
    }),
  });

  if (!response.ok) {
    throw new Error("Failed to create flight");
  }

  return response.json();
}

export async function getFlight(id: string): Promise<FlightSimulation> {
  const response = await fetch(`${API_BASE_URL}/flights/${id}`);

  if (!response.ok) {
    throw new Error("Failed to load flight");
  }

  return response.json();
}

export async function getFlightHistory(id: string): Promise<FlightMetric[]> {
  const response = await fetch(`${API_BASE_URL}/flights/${id}/history`);

  if (!response.ok) {
    throw new Error("Failed to load flight history");
  }

  return response.json();
}
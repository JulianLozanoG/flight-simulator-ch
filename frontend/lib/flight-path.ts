import { RoutePoint } from "./types";

export function getPointAtProgress(route: RoutePoint[], progress: number): RoutePoint {
  if (route.length === 0) return { x: 0, y: 0 };
  if (route.length === 1) return route[0];

  const clamped = Math.max(0, Math.min(1, progress));
  const totalSegments = route.length - 1;
  const scaled = clamped * totalSegments;
  const segmentIndex = Math.min(Math.floor(scaled), totalSegments - 1);
  const localT = scaled - segmentIndex;

  const start = route[segmentIndex];
  const end = route[segmentIndex + 1];

  return {
    x: start.x + (end.x - start.x) * localT,
    y: start.y + (end.y - start.y) * localT,
  };
}

export function buildSvgPath(route: RoutePoint[]): string {
  if (route.length === 0) return "";
  if (route.length === 1) return `M ${route[0].x} ${route[0].y}`;

  const [first, ...rest] = route;
  let path = `M ${first.x} ${first.y}`;

  for (let i = 0; i < rest.length; i++) {
    const prev = route[i];
    const current = route[i + 1];
    const midX = (prev.x + current.x) / 2;
    const midY = (prev.y + current.y) / 2;

    path += ` Q ${prev.x} ${prev.y} ${midX} ${midY}`;
  }

  const last = route[route.length - 1];
  path += ` T ${last.x} ${last.y}`;

  return path;
}
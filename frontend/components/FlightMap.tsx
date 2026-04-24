import { buildSvgPath, getPointAtProgress } from "@/lib/flight-path";
import { FlightSimulation } from "@/lib/types";

type FlightMapProps = {
  flight: FlightSimulation;
};

export function FlightMap({ flight }: FlightMapProps) {
  const currentPoint = getPointAtProgress(flight.route, flight.progress);
  const svgPath = buildSvgPath(flight.route);

  return (
    <div className="rounded-3xl border border-white/10 bg-slate-950/60 p-4 shadow-2xl backdrop-blur">
      <div className="mb-4 flex items-center justify-between px-2">
        <h2 className="text-lg font-semibold">Flight Path</h2>
        <span className="rounded-full bg-cyan-400/15 px-3 py-1 text-xs text-cyan-300">
          Progress {Math.round(flight.progress * 100)}%
        </span>
      </div>

      <div className="overflow-hidden rounded-2xl border border-white/10 bg-slate-900">
        <svg
          viewBox="0 0 860 420"
          className="h-[420px] w-full"
          role="img"
          aria-label="Flight route map"
        >
          <defs>
            <linearGradient id="routeGradient" x1="0%" y1="0%" x2="100%" y2="0%">
              <stop offset="0%" stopColor="#22d3ee" />
              <stop offset="100%" stopColor="#60a5fa" />
            </linearGradient>

            <radialGradient id="planeGlow" cx="50%" cy="50%" r="50%">
              <stop offset="0%" stopColor="#f8fafc" stopOpacity="1" />
              <stop offset="100%" stopColor="#f8fafc" stopOpacity="0" />
            </radialGradient>

            <pattern id="grid" width="40" height="40" patternUnits="userSpaceOnUse">
              <path
                d="M 40 0 L 0 0 0 40"
                fill="none"
                stroke="rgba(255,255,255,0.06)"
                strokeWidth="1"
              />
            </pattern>
          </defs>

          <rect width="860" height="420" fill="#020617" />
          <rect width="860" height="420" fill="url(#grid)" />

          <path
            d={svgPath}
            fill="none"
            stroke="url(#routeGradient)"
            strokeWidth="4"
            strokeLinecap="round"
            strokeDasharray="10 10"
          />

          {flight.route.map((point, index) => (
            <circle
              key={`${point.x}-${point.y}-${index}`}
              cx={point.x}
              cy={point.y}
              r={index === 0 || index === flight.route.length - 1 ? 8 : 4}
              fill={
                index === 0
                  ? "#22c55e"
                  : index === flight.route.length - 1
                    ? "#ef4444"
                    : "#38bdf8"
              }
              stroke="rgba(255,255,255,0.25)"
              strokeWidth="2"
            />
          ))}

          <circle cx={currentPoint.x} cy={currentPoint.y} r="14" fill="url(#planeGlow)" opacity="0.6" />

          <g transform={`translate(${currentPoint.x}, ${currentPoint.y})`}>
            <text
              x="0"
              y="6"
              textAnchor="middle"
              fontSize="18"
              fill="#f8fafc"
              style={{ filter: "drop-shadow(0 0 6px rgba(255,255,255,0.35))" }}
            >
              ✈
            </text>
          </g>

          <text x="40" y="60" fill="#22c55e" fontSize="14" fontWeight="600">
            {flight.origin}
          </text>
          <text x="790" y="120" fill="#ef4444" fontSize="14" fontWeight="600">
            {flight.destination}
          </text>
        </svg>
      </div>
    </div>
  );
}
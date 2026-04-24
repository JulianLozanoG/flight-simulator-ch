import { FlightPhase } from "@/lib/types";

type FlightTimelineProps = {
  currentPhase: FlightPhase;
};

const phases: FlightPhase[] = [
  "BOARDING",
  "TAXI_OUT",
  "TAKEOFF_CLIMB",
  "CRUISE",
  "DESCENT",
  "LANDING",
  "TAXI_IN",
  "COMPLETED",
];

export function FlightTimeline({ currentPhase }: FlightTimelineProps) {
  const currentIndex = phases.indexOf(currentPhase);

  return (
    <div className="rounded-3xl border border-white/10 bg-white/5 p-5 shadow-lg backdrop-blur">
      <div className="mb-4 flex items-center justify-between">
        <h2 className="text-lg font-semibold">Flight Timeline</h2>
        <span className="text-sm text-slate-300">{currentPhase}</span>
      </div>

      <div className="flex flex-wrap gap-3">
        {phases.map((phase, index) => {
          const isActive = phase === currentPhase;
          const isCompleted = index < currentIndex;

          return (
            <div
              key={phase}
              className={[
                "rounded-full border px-4 py-2 text-sm transition",
                isActive
                  ? "border-cyan-400 bg-cyan-400/20 text-cyan-200"
                  : isCompleted
                    ? "border-emerald-500 bg-emerald-500/15 text-emerald-300"
                    : "border-white/10 bg-slate-950/40 text-slate-400",
              ].join(" ")}
            >
              {phase}
            </div>
          );
        })}
      </div>
    </div>
  );
}
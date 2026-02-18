# Orbit Tap Rush — Product Spec

## One-liner
A one-thumb timing game where players tap at the exact moment an orbiting marker overlaps a moving target beacon.

## Core loop
1. Yellow marker continuously orbits a ring.
2. Green target beacon shifts each successful tap.
3. Player taps when marker overlaps beacon.
4. Hit = score + combo. Miss = life lost.
5. 3 misses ends run. Tap to restart.

## Session design
- 20–90 second sessions
- Instant restart
- No onboarding friction

## Success criteria for MVP
- Responsive tap feedback
- Increasing challenge via speed ramp
- Stable 60fps animation on typical devices
- Fully playable in portrait

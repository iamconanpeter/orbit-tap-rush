# Orbit Tap Rush — Technical Plan (Retrofit)

## Architecture
- `OrbitGameEngine` (pure Kotlin rules/state)
  - Timing hit detection
  - Combo/score/perfect-hit progression
  - Shield and clutch-save fairness systems
- `OrbitRushView` (Android Canvas rendering + input)
  - Frame loop and drawing
  - HUD rendering for progression/fairness state
  - Touch interaction and restart flow
- `MainActivity`
  - Lightweight host for custom view

## Retrofit implementation notes
1. Extend `TapResult` with `CLUTCH_SAVE`.
2. Track `clutchSaves` in engine snapshot/state.
3. Award one clutch token at combo milestones (`combo % 7 == 0`, capped at 1).
4. On lethal miss (`misses == missLimit - 1`), consume clutch token and avoid game over.
5. Surface clutch state in HUD and feedback text.

## Performance and reliability constraints
- Clamp update delta to `<= 0.05f` to prevent resume spikes.
- Continue allocation-light draw loop (reuse paints, primitive geometry only).
- Keep engine deterministic and unit-testable via helper test hooks.

## Test strategy
- Keep existing core tests: perfect scoring, shield behavior, level ramp, restart.
- Add retrofit tests:
  - combo-seven grants clutch token
  - clutch token prevents lethal miss game-over
- Test type: local JUnit4 only (no instrumentation needed for rule coverage).

## Build/validation commands
```bash
./gradlew test assembleDebug
```

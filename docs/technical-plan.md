# Orbit Tap Rush — Retrofit Technical Plan

## Stack
- Kotlin + Android SDK Canvas View
- Min SDK 24 / Target 34
- JUnit4 logic tests

## Retrofit Architecture Changes

### Engine (`OrbitGameEngine`)
Add progression/fairness states:
- `level`, `hitsThisLevel`
- `shieldCharges` (combo-earned)
- `perfectHits`
- dynamic `hitWindow`
- `TapResult` enum (`HIT`, `PERFECT_HIT`, `MISS`, `SHIELDED_MISS`, `GAME_OVER`)

Rules:
- Combo every 5 grants up to 2 shield charges.
- Miss consumes shield first if available (fairness control).
- Perfect hit threshold = `hitWindow * 0.35`.
- Level-up tightens hit window and increases angular speed.

### View (`OrbitRushView`)
- Render additional HUD rows: level, shield, perfect hits, best score, daily target.
- Store best score in `SharedPreferences`.
- Show contextual feedback string per tap result.

## Differentiation vs low-quality clones (implementation mapping)
- **Clone weakness:** flat scoring loop → **Fix:** perfect-hit + level multipliers.
- **Clone weakness:** harsh fail-state → **Fix:** earned shield forgiveness.
- **Clone weakness:** no reason to return → **Fix:** persistent best + daily target objective.

## Test Strategy
1. Perfect-hit result and scoring path.
2. Shield earn + shielded miss behavior.
3. Level-up causes hit-window shrink.
4. Restart clears progression state.

## Performance / Quality Notes
- Keep per-frame object allocation minimal.
- Continue delta clamp to avoid frame-jump unfairness.
- Avoid additional layout passes (Canvas-only rendering retained).

## Validation Commands
- `./gradlew test`
- `./gradlew assembleDebug`
- combined: `./gradlew test assembleDebug`

# Orbit Tap Rush — Retrofit Tasks

## P0 (required before ship)
- [x] Re-run plan-mode docs with Q&A discovery
- [x] Add differentiation/retention/quality-bar definitions
- [x] Implement clutch-save fairness mechanic in game engine
- [x] Update HUD + feedback text for clutch state
- [x] Add unit tests for new progression and fairness logic
- [x] Run `./gradlew test assembleDebug`
- [x] Commit and push retrofit changes

## P1 (next pass)
- [ ] Add haptic and lightweight sound feedback for tap outcomes
- [ ] Add event telemetry hooks (best score, run duration, fail reason)

## Validation notes
- Retrofit requires green local build/tests before push.
- Keep gameplay deterministic and fair under increasing speed.

# Orbit Tap Rush — Retrofit Tasks

## PLAN MODE gate
- [x] Q&A discovery documented in `docs/spec.md`
- [x] Differentiation + retention checklist documented
- [x] Scope guardrail + post-MVP split documented

## Implementation
- [x] Add level progression and dynamic hit window to engine
- [x] Add perfect-hit scoring tier
- [x] Add combo-earned shield system for fair fail-state control
- [x] Expand snapshot/state for HUD and tests
- [x] Add best-score persistence and daily target in UI
- [x] Add dynamic per-tap feedback text

## Testing
- [x] Add unit test: perfect-hit flow
- [x] Add unit test: shielded miss fairness
- [x] Add unit test: level-up window shrink
- [x] Add unit test: restart progression reset

## Validation + release
- [x] Run `./gradlew test assembleDebug`
- [x] Commit retrofit changes
- [x] Push to same GitHub repo
- [x] Update `research/game_factory_status.json` retrofitStatus

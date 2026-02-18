# Orbit Tap Rush — Technical Plan

## Stack
- Kotlin + Android SDK (View-based rendering)
- Min SDK 24, Target/Compile 34
- JUnit4 for game logic tests

## Architecture
- `OrbitGameEngine`: deterministic pure Kotlin game rules/state
- `OrbitRushView`: render loop + touch input + HUD
- `MainActivity`: host view

## Performance choices
- Avoid allocations in draw loop
- Clamp frame delta to avoid jumps
- Keep art primitive (Canvas circles/text)

## Test strategy
- Unit test scoring/combo progression
- Unit test miss/game-over logic
- Unit test restart reset behavior

## Build & run commands
- `./gradlew test`
- `./gradlew assembleDebug`

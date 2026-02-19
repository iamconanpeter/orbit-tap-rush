# Orbit Tap Rush — Retrofit Plan Mode Spec

## Plan Mode Status
- **Mode:** Codex PLAN MODE (retrofit pass)
- **Goal:** Upgrade an already-shipped MVP with better replayability, retention, fairness, and polish while staying lightweight.
- **Scope guardrail:** No heavy new screens, keep one-thumb core loop, preserve 2D low-asset implementation.

## Q&A Discovery (required)

### 1) Core fantasy and 10-second hook
**Q:** What is the immediate fantasy?  
**A:** “I can lock into rhythm and land perfect timing hits.” In 10 seconds, the player sees two orbit points and understands the tap timing challenge.

### 2) Why users come back (daily/weekly loop)
**Q:** Why return after first session?  
**A:** Daily score target + persistent best-score chase + mastery of perfect-hit streaks.

### 3) Session length targets
**Q:** How long should sessions run?  
**A:** 30s quick attempts, 2m focused streak runs, 5m “beat best score” loops.

### 4) Skill vs luck balance
**Q:** Is outcome mostly skill?  
**A:** High skill. Randomness is limited to target rotation progression; scoring comes from timing precision and combo maintenance.

### 5) Fail-state fairness and frustration controls
**Q:** How do we reduce rage quits?  
**A:** Add **shielded misses** earned from combo streaks; one bad tap can be forgiven without ending momentum.

### 6) Difficulty ramp and onboarding
**Q:** How does challenge scale?  
**A:** Progressive levels tighten hit window and increase motion speed. Onboarding remains implicit via first-round pacing.

### 7) Distinctive mechanic vs common Android clones
**Q:** What separates this from disposable tap-timers?  
**A:** Precision tiering (perfect hits), shield economy, and daily target objective integrated into a single-screen loop.

### 8) Art/animation scope feasible for small team
**Q:** Is this realistic for vibe-coding?  
**A:** Yes. Primitive circles/text only; no sprite pipeline needed.

### 9) Audio/feedback plan
**Q:** How will feedback feel alive without heavy assets?  
**A:** Text pulse feedback per tap result (Perfect / Hit / Shielded miss / Miss), plus HUD clarity for level/shield/perfect count.

### 10) Monetization-safe design
**Q:** Can this support monetization later without dark patterns?  
**A:** Yes: optional rewarded continue or cosmetic themes later. No forced ads required for current loop.

### 11) Technical constraints and performance budgets
**Q:** Budget constraints?  
**A:** 60fps target, no per-frame allocations, no network dependency, min SDK 24.

## Assumptions
- Assumption: casual Android players respond to daily target + personal-best loops similarly to iOS timing audiences.
- Assumption: shield forgiveness increases retention more than it lowers challenge satisfaction.

## USP (1 line)
A clean one-thumb orbit timing game with perfect-hit mastery and combo-earned miss shields.

## 3 Differentiators
1. Combo-earned shield mechanic (fairness without removing challenge).
2. Perfect-hit tier scoring for high-skill expression.
3. Daily target overlay for lightweight return motivation.

## 3 Retention Hooks
1. Persistent best-score chase.
2. Daily target (changes by day-of-year seed).
3. Progression loop via levels that tighten hit window.

## 3 Quality Bars
1. Readable HUD: score/level/shield/perfect/misses visible at a glance.
2. Immediate tap-result feedback text with distinct states.
3. Stable animation cadence with delta clamping.

## MVP Retrofit Scope
### In scope
- Level progression and dynamic hit window
- Perfect hit scoring tier
- Shielded miss system
- Best-score persistence + daily target UI
- Updated unit tests for progression/fairness

### Post-MVP
- Haptics + sound pack
- Global leaderboard + asynchronous challenge ghosts
- Cosmetic ring themes and particle bursts

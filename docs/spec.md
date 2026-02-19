# Orbit Tap Rush — Retrofit Spec (Plan Mode)

## Plan-mode output status
- `docs/spec.md` ✅
- `docs/technical-plan.md` ✅
- `docs/tasks.md` ✅

## Product summary
Orbit Tap Rush is a one-thumb reflex game where players sync taps with a fast orbiting marker while the target keeps drifting. Retrofit goal: keep the original instantly-playable core, but add better fairness, retention hooks, and feedback juice.

## Q&A discovery pass (assumptions marked)
1. **Core fantasy + 10s hook**  
   _"Nail perfect timing under pressure and recover clutch saves before the ring outruns you."_
2. **Why users come back (daily/weekly loop)**  
   Beat personal best, clear rotating daily score target, improve consistency on perfect-hit chains.
3. **Session length targets**  
   30s quick retry; 2m focus run; 5m personal-best chase with repeated restarts.
4. **Skill vs luck balance**  
   High-skill timing focus; low luck. Target motion is deterministic but difficult.
5. **Fail-state fairness + frustration controls**  
   Shield charges from streaks absorb standard misses; new clutch save token can prevent one lethal miss.
6. **Difficulty ramp + onboarding**  
   Gradual level-up shrinks hit window and increases speed. First run instruction line remains persistent.
7. **Distinctive mechanic vs common clones**  
   Orbiting dual-motion timing + streak-earned fairness tools (shield + clutch save) instead of random powerups.
8. **Art/animation scope (small team feasible)**  
   Minimalist canvas visuals, color-led state readability, no heavy assets.
9. **Audio/feedback plan (assumption)**  
   Assumption: visual-first MVP; audio haptics deferred post-MVP to keep scope tight.
10. **Monetization-safe design**  
   No forced ads/paywalls in core loop, no dark patterns.
11. **Technical constraints + performance budgets**  
   Keep 60fps on mid-range Android, avoid draw allocations, clamp frame delta at 50ms.

## Differentiation + retention checklist
- **USP:** Rhythm-like timing game where streak mastery grants defensive resources instead of pure punishment.
- **3 differentiators:**
  1. Moving target + moving player marker (dual trajectory tension)
  2. Perfect-hit bonus scoring
  3. Clutch-save anti-tilt mechanic for end-of-run fairness
- **3 retention hooks:**
  1. Daily target badge/checkmark
  2. Combo-driven shield economy
  3. Clutch-save unlock every 7-hit streak for comeback moments
- **3 quality bars:**
  1. Immediate readable feedback text for each tap outcome
  2. Clear top HUD for score/progression/safety resources
  3. Smooth motion with stable frame pacing

## MVP scope guardrail
### In-scope retrofit
- Upgrade design docs to plan-mode standard.
- Add clutch-save fairness mechanic.
- Ensure HUD communicates new progression/fairness state.
- Expand unit tests around progression + fail-state behavior.

### Post-MVP
- Haptics + audio feedback tiers.
- Leaderboard/social compare.
- Cosmetic themes/unlockables.

## Validation gate
- Spec complete ✅
- Technical plan complete ✅
- Priority tasks complete ✅
- Test strategy defined ✅

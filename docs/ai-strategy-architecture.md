# Experimental Strategic AI Architecture

This document describes the experimental modular AI layer added on top of the existing XMage Mad AI. The goal is to improve decisions without losing the original AI as a control set.

## Control Boundary

The original Mad AI remains the baseline evaluator. Strategic behavior should live behind the strategic player path and the `xmage.ai.strategy.*` properties. Shared engine or control-AI changes should be limited to tracing, bug fixes, or generic utilities that do not alter the control AI's decisions.

Hand-quality discard targeting follows the same boundary: the shared target selector keeps its original default ordering, and `ComputerPlayerMadStrategic` opts into the hand-quality ordering for itself and its simulated players only when `xmage.ai.strategy.handQuality.apply=true` and trace-only mode is off.

The intended layering is:

```text
rules engine
  -> legal actions
  -> original Mad AI candidate search and base score
  -> strategic scoring engine
  -> ordered scoring modules
  -> traceable raw/applied modifiers
```

The AI companion uses the same strategic scoring path so human evaluation can see the same signals that the experimental AI uses.

## Main Code Layout

Strategic AI classes live mainly under:

```text
Mage.Server.Plugins/Mage.Player.AI.MA/src/mage/player/ai/
Mage.Server.Plugins/Mage.Player.AI.MA/src/mage/player/ai/scoring/
Mage.Server.Plugins/Mage.Player.AI.MA/src/mage/player/ai/util/
```

Important entry points:

| Area | File |
| --- | --- |
| Strategic player | `ComputerPlayerMadStrategic.java` |
| Companion advisor | `AiCompanionAdvisor.java` |
| Scoring engine | `scoring/AiScoringEngine.java` |
| Module registry | `scoring/AiScoreModuleRegistry.java` |
| Module order | `scoring/AiScoringEngineFactory.java` |
| Module ids | `scoring/AiScoreModuleId.java` |
| Shared scoring helpers | `scoring/AiScoreSupport.java` |
| Attack trigger projection | `util/AttackTriggerProjection.java` |

## Scoring Model

Each top-level candidate action starts with the original AI's base score. The strategic engine then runs each enabled `AiScoreModule` in a stable order. A module returns:

| Field | Meaning |
| --- | --- |
| `baseScore` | Score from the underlying search. |
| `rawModifier` | What the module thinks should change. |
| `appliedModifier` | What is actually added to the candidate score. |
| `contributions` | Human-readable labels and details for traces/companion. |

Most modules default to monitor mode: enabled, but not applied. A module influences choices only when global strategy scoring is active and the module's `.apply` property is true.

The final applied modifier is also capped by `xmage.ai.strategy.maxScoreModifier` after all modules have run. Individual module caps prevent one module from dominating; the global cap prevents many correct-looking modules from stacking into an oversized shove for the same action.

Deck roles intentionally separate card function from deck mechanics. For sacrifice decks, `SACRIFICE_FODDER_PROVIDER` means the card creates expendable material, while `SACRIFICE_FODDER` means the card itself is expendable, recursive, or death-value material. This keeps token makers from being explained as though the source card itself should be spent.

## Module Groups

| Module | Purpose |
| --- | --- |
| `deck-strategy` | Applies deck-profile and card-role priors. |
| `ffa-table-threat` | Estimates multiplayer table danger. |
| `ffa-target-priority` | Scores target selection in free-for-all games. |
| `target-quality` | Scores targeted actions by target polarity and target quality. |
| `phase-strategy` | Adds phase/step intent such as own pre-draw caution or opponent upkeep disruption. |
| `combat-pressure` | Values attacks, projected attack triggers, and combat pressure. |
| `defense-reserve` | Penalizes leaving too little defense in FFA. |
| `commander-lifecycle` | Values commander protection and commander tax discipline. |
| `game-stage` | Adjusts early/mid/late game priorities. |
| `board-role` | Interprets the current board shape. |
| `threat-projection` | Estimates near-future opponent pressure. |
| `resource-development` | Values ramp, land development, and mana source growth. |
| `color-access` | Values mana development by the hand and commander colors it unlocks. |
| `hand-plan` | Values card access and hand conversion. |
| `hand-quality` | Values actual hand-card keep quality and discard losses, using structured dynamic power/toughness values plus bounded future-potential floors. |
| `interaction-timing` | Rewards stack interaction and discourages low-value interaction. |
| `sacrifice-and-cost` | Scores sacrifice/cost patterns against deck and board context. |
| `search-and-tutor` | Values search effects generically. |
| `token-swarm` | Recognizes board-wide token pressure. |
| `combo-progress` | Gives generic credit for resource/combo enabling actions, including finite repeatable buyback-loop progress toward visible combat lethal. |
| `win-closure` | Reduces overkill and encourages closing won positions. |
| `political-memory` | Placeholder for FFA opponent-history signals. |
| `alternate-loss` | Tracks non-life loss pressure. |

## Phase Strategy

`PhaseStrategyScoreModule` is the first explicit per-step strategy module. It is deliberately conservative and generic:

| Step context | Signal |
| --- | --- |
| Own untap/upkeep before draw | Penalize meaningful mana spend before seeing the draw, unless the action is cheap, mana-only, stack-relevant, card access, or interaction. |
| Opponent pre-draw/upkeep | Reward real interaction that harms/disrupts the opponent before upkeep/draw resources matter. |
| Main phases | Give small credit to land plays, mana development, and ordinary spell deployment in normal main-phase windows. |
| Combat steps | Penalize non-urgent development during combat so combat-only decisions are easier to compare. |

Future phase work should expand this module or split it into focused phase strategies only when the logic becomes too large. Examples:

| Future phase strategy | Purpose |
| --- | --- |
| `PreCombatMainStrategy` | Prefer setup, mana, and sorcery-speed development before combat only when it does not reveal unnecessary information or tap key blockers. |
| `DeclareAttackersStrategy` | Choose pressure based on lethal, overkill, attack triggers, crack-back risk, and FFA politics. |
| `PostCombatMainStrategy` | Prefer deploying noncombat permanents after combat when attacks were already chosen. |
| `EndStepStrategy` | Use instant-speed card access or mana sinks when mana would otherwise be wasted. |
| `OpponentTurnStrategy` | Hold interaction unless a stack object, attack, upkeep trigger, or high-value threat requires action. |

## Configuration

Packaged defaults live in `Mage.Server/release/config/ai-strategy.properties`. JVM `-D` properties override that file.

Common properties:

| Property | Meaning |
| --- | --- |
| `xmage.ai.strategy` | Master gate for strategic scoring. |
| `xmage.ai.strategy.traceOnly` | Record strategic signals without applying them. |
| `xmage.ai.trace` | Write AI decision JSONL traces. |
| `xmage.ai.strategy.ffaTarget.apply` | Apply FFA target priority modifiers. |
| `xmage.ai.strategy.targetQuality.apply` | Apply target-quality modifiers for removal, protection, and buffs. |
| `xmage.ai.strategy.phaseStrategy.apply` | Apply phase strategy modifiers. |
| `xmage.ai.strategy.interactionTiming.apply` | Apply stack/interaction timing modifiers. |
| `xmage.ai.strategy.combatPressure.apply` | Apply combat pressure modifiers. |
| `xmage.ai.strategy.defenseReserve.apply` | Apply FFA defense reserve modifiers. |
| `xmage.ai.strategy.resourceDevelopment.apply` | Apply ramp, land, and mana source modifiers. |
| `xmage.ai.strategy.colorAccess.apply` | Apply color-fixing modifiers for hand and commander castability. |
| `xmage.ai.strategy.handPlan.apply` | Apply hand-size/card-access planning modifiers. |
| `xmage.ai.strategy.handQuality.apply` | Apply hand-card keep-quality modifiers and strategic discard targeting for draw/discard decisions. |
| `xmage.ai.strategy.winClosure.apply` | Apply overkill/win-closure modifiers. |

`xmage.ai.strategy.maxScoreModifier` is the global cap for the final strategic adjustment. Keep this modest when many `.apply` switches are enabled together.

Strategic search budget controls are enabled by default for `ComputerPlayerMadStrategic` only:

| Switch | Meaning |
| --- | --- |
| `-DisableAdaptiveSearchBudget` | Disable strategic-only adaptive depth/node/heap guards. |
| `-SearchBudgetMinDepth` | Minimum strategic search depth after budget reductions. |
| `-SearchBudgetLargeBoardThreshold` | Commander battlefield size that caps strategic depth to 6. |
| `-SearchBudgetHugeBoardThreshold` | Commander battlefield size that caps strategic depth to 5. |
| `-SearchBudgetHeapRatio` | Used-heap ratio where strategic search stops deeper simulation copies. |
| `-DisableCastVariants` | Disable strategic-only cast cost variants such as buyback or replicate candidate branches. |

Strategic cast variants expand the action surface only for `ComputerPlayerMadStrategic`. A spell can be evaluated as a normal cast and as one or more preselected cost-plan casts, such as buyback or bounded replicate counts, without changing the human prompt flow or original Mad AI control path. Variants can also advertise that they preserve their source card; in precombat combo-progress windows, the strategic AI can prefer those source-preserving lines until visible combat lethal is available.

For human evaluation, start conservative: enable `xmage.ai.trace=true` and `xmage.ai.strategy.traceOnly=true`, then apply one or two modules at a time by setting their `.apply` properties. After AI code changes, install the relevant Maven artifacts before launching; the server resolves player AI classes through the Maven runtime classpath.

## Trace And Monitor Workflow

Use the companion for immediate qualitative feedback, then use JSONL traces for aggregate review. Reporting and timeline helpers can remain local tooling; they are not required by the runtime branch.

Candidate and chosen rows include bounded feature snapshots when tracing is enabled:

| Field | Meaning |
| --- | --- |
| `startFeatureSnapshot` | Per-player life, hand, board, mana source, commander, and top-threat context before the candidate. |
| `finalFeatureSnapshot` | Same compact context after the simulated candidate/best line. |
| `phaseIntent` | Coarse strategic timing bucket used by timeline views. |

The timeline side panel shows the selected decision's snapshot and feature deltas. Older JSONL traces without these fields remain valid.

If a trace directory contains multiple historical games, keep aggregate and per-game summaries keyed by `gameId` when using local reporting tools.

Look for:

| Pattern | Meaning |
| --- | --- |
| Large raw modifiers | A module may be overpowering the base search. |
| Large applied modifiers | A live module is changing decisions strongly. |
| Classifier influence on applied changes | Deck role/package/profile labels are steering real choices; audit these before blaming tactical modules. |
| Repeated negative phase signals | The AI may be acting in the wrong window. |
| Would-change decisions | Monitor-mode modules are likely to alter choices when applied. |
| Companion/action mismatch | Companion may be seeing a different action surface than the actual AI. |

The monitor report includes `Classifier Influence On Applied Changes`. This section compares the base choice with the strategy-adjusted choice and aggregates the net role/package/profile/hidden-info delta that made the applied choice more attractive. Use it to validate classifier work against decisions the AI actually changed.

Mechanic labels such as `mechanic:TRIBAL:Dragon`, `mechanic:SUBTYPE:Dragon`, and `mechanic:SACRIFICE` are currently emitted as raw deck-context signals only. They are intentionally excluded from the applied `deck-strategy` modifier while we validate their quality. This lets the companion and trace reports show whether a card is aligned with the deck's identity without letting noisy generated tags overpower tactical evaluation.

## Adding Or Updating A Module

Use this checklist:

1. Add or update a focused `AiScoreModule`.
2. Keep labels stable and descriptive, for example `phase-strategy:own-pre-draw-hold`.
3. Add system properties for `enabled`, `apply`, and `maxScoreModifier`.
4. Register the module in `AiScoreModuleId`, `AiScoreModuleRegistry`, and `AiScoringEngineFactory`.
5. Document any new `enabled`, `apply`, and `maxScoreModifier` properties.
6. Keep the first version monitor-friendly: trace raw signals before applying them.
7. Compile with:

```powershell
mvn -pl Mage.Server.Plugins/Mage.Player.AI.MA -am -DskipTests compile
```

## Design Rules

Keep modules generic. Prefer "Ghostly Prison-like attack tax" over a hard-coded Ghostly Prison special case.

Keep scoring modest. A module should explain its contribution and stay capped. If a module needs huge scores, the base evaluation or game-state model probably needs a deeper fix.

Deck-profile scoring should be treated as a prior, not a replacement for tactical evaluation. Multi-role classifier pileups are capped in the strategic path so a noisy card classification cannot spend the full deck-strategy budget just by matching many labels.

Roles and mechanics are separate layers. Roles are functional jobs such as `REMOVAL`, `RAMP`, or `SACRIFICE_OUTLET` and can directly influence strategic scoring. Mechanics are string keys such as `SUBTYPE:Dragon`, `TRIBAL:Dragon`, `TOKENS`, or `GRAVEYARD`; they explain deck identity and synergy context. Mechanics should stay modest and monitor-first until trace reports show that they consistently improve decisions.

Prefer structured engine data over text parsing. Text matching is acceptable for early generic signals, but should be replaced with structured ability/cost/effect inspection when practical.

Preserve observability. Every nonzero strategic signal should have a stable label and detail string that helps explain the decision in traces and companion output.

Preserve the control AI. Do not move experimental behavior into the original Mad AI unless it is explicitly intended to become baseline behavior.

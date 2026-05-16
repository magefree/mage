# Experimental Strategic AI Roadmap

This roadmap is a handoff for future agents working on the modular strategic AI. It assumes the original Mad AI remains the control path and all new behavior stays behind `ComputerPlayerMadStrategic`, `AiCompanionAdvisor`, `mage.player.ai.scoring`, `mage.player.ai.util`, and `xmage.ai.strategy.*` switches.

The goal is not to make one giant evaluator smarter. The goal is to keep extracting named, observable decision services that can be tested, traced, compared, and selectively applied.

## Priority 0: Strategic AI Search Stabilization

This is the next implementation priority before adding more decision quality modules. The April 29 lockup showed `ComputerPlayerMadStrategic` timing out while its inherited Mad simulation worker was deep in `GameImpl.createSimulationForAI -> GameState.copy`, with the heap dominated by copied mana, abilities, maps, and token/effect objects. The strategic AI is active, but it still depends on this inherited recursive search.

Purpose: keep long Commander games responsive by bounding strategic AI simulation cost, especially on large boards and spell-copy/token/resource-heavy turns.

Current implementation status:

- `StrategicSearchBudgetPolicy` provides a pure strategic-only depth/node/heap policy with unit coverage.
- `ComputerPlayerMadStrategic` applies the policy without changing the original Mad AI entry point.
- Search traces emit `search-budget`, `copy-skipped`, and timeout fallback node events when tracing is enabled.
- `Mage.Server/release/config/ai-strategy.properties` exposes adaptive budget defaults, with JVM `-D` overrides for local runs.
- Remaining hardening: build a full lockup/stress reproduction scenario and expand cancellation checks if traces still show deep copies after a stop reason.

### 0.1 Strategic-Only Adaptive Search Budget

Problem:

- Late Commander boards make depth 10 too expensive.
- Timeout currently logs the issue after the server has already spent a long time and large heap on simulation copies.
- The strategic AI needs to degrade search depth before it enters pathological branches.

Implementation target:

- Add a strategic-only budget policy, for example `StrategicSearchBudgetPolicy`, used by `ComputerPlayerMadStrategic` or its immediate strategic path.
- Do not change original Mad AI depth/budget behavior.
- Inputs:
  - game type / Commander detection
  - turn number
  - battlefield permanent count
  - stack size
  - legal action count
  - current maxDepth / skill
  - available heap pressure if cheap to query
- Outputs:
  - effective depth
  - max nodes
  - per-top-level-candidate time budget
  - reason labels for trace/logs

Suggested first policy:

| Condition | Strategic depth cap | Reason |
| --- | --- | --- |
| Commander and battlefield >= 40 | 6 | `search-budget:large-commander-board` |
| Commander and battlefield >= 55 | 5 | `search-budget:huge-commander-board` |
| Turn >= 35 and battlefield >= 35 | 6 | `search-budget:late-game-board` |
| Stack not empty | keep higher depth if stack is small | stack interaction can be tactically urgent |
| Legal actions > 25 | reduce by 1 more | branch factor is already high |

Properties:

| Property | Default | Meaning |
| --- | --- | --- |
| `xmage.ai.strategy.searchBudget.enabled` | `true` | Enables strategic-only adaptive budget. |
| `xmage.ai.strategy.searchBudget.apply` | `true` | Applies effective depth/node changes. |
| `xmage.ai.strategy.searchBudget.minDepth` | `5` | Never reduce below this in normal play. |
| `xmage.ai.strategy.searchBudget.largeBoardThreshold` | `45` | Commander board size threshold. |
| `xmage.ai.strategy.searchBudget.hugeBoardThreshold` | `65` | Severe board size threshold. |
| `xmage.ai.strategy.searchBudget.trace` | `true` | Emit budget decisions to trace/log. |

Trace/log output:

- `search-budget:effective-depth`
- `search-budget:max-nodes`
- `search-budget:large-commander-board`
- `search-budget:legal-action-pressure`

Headless tests:

- Unit test the policy as a pure helper: board size/turn/action count -> effective depth.
- Test that non-strategic Mad AI does not use the policy.
- Test that strategic AI with a large Commander board caps depth without disabling legal actions.

### 0.2 Simulation Cancellation Hardening

Problem:

- `FutureTask.cancel(true)` interrupts the worker, but the dump showed the worker still inside deep game copying.
- Interruption is checked in parts of `addActions`, but not before every expensive copy path.

Implementation target:

- Before `game.createSimulationForAI()` in strategic search, check:
  - thread interrupted
  - decision deadline exceeded
  - candidate deadline exceeded
  - heap pressure guard if available
- Immediately after `createSimulationForAI()`, check again before activation/effects.
- If stop is requested, evaluate current node without deeper copy.
- Add trace reason `search-budget:copy-skipped`.

Candidate code location:

- `ComputerPlayer6.addActions(...)` currently creates copies before action activation.
- Prefer strategic-only hooks or overridable methods so the original control path is not changed.
- If a shared hook is unavoidable, default it to existing behavior and override in `ComputerPlayerMadStrategic`.
- For hand-quality discard targeting, the shared selector default must remain original Mad AI ordering; only `ComputerPlayerMadStrategic` should opt into keep-score sorting, and only when `xmage.ai.strategy.handQuality.apply=true` with trace-only mode off.

Headless tests:

- Pure test for stop policy.
- If practical, construct a strategic player with an expired deadline and assert it does not call deeper simulation.

### 0.3 Heap Pressure Guard

Problem:

- Heap histogram showed millions of copied `Mana`, `SpellAbility`, `ManaCostsImpl`, token/effect, and map objects.
- Once heap pressure is high, continuing a recursive simulation can disconnect clients even if a timeout eventually fires.

Implementation target:

- Add a cheap heap guard used only by strategic search:

```text
usedHeap / maxHeap >= threshold -> stop deeper recursion and evaluate current state
```

Properties:

| Property | Default | Meaning |
| --- | --- | --- |
| `xmage.ai.strategy.searchBudget.heapGuard.enabled` | `true` | Enable heap pressure guard. |
| `xmage.ai.strategy.searchBudget.heapGuard.maxUsedRatio` | `0.90` | Stop deeper strategic simulation above this ratio. |
| `xmage.ai.strategy.searchBudget.heapGuard.minFreeMb` | `768` | Stop if free heap is below this floor. |

Trace labels:

- `search-budget:heap-pressure`
- `search-budget:heap-stop`

Headless tests:

- Unit test heap guard threshold logic with injected values.
- Avoid tests that depend on actual JVM heap size.

### 0.4 Timeout Result Fallback

Problem:

- When timeout happens, `addActionsTimed()` returns `0` after nulling root.
- In late game, returning `0` may distort choices and hides the best candidate known so far.

Implementation target:

- Preserve best completed top-level candidate before timeout.
- On timeout, return best completed score/action if available.
- Record `timeoutFallback=true` in trace.
- If no candidate completed, prefer pass/fallback with explicit trace reason.

Trace labels:

- `search-timeout:best-completed-candidate`
- `search-timeout:no-completed-candidate`

Headless tests:

- Simulated timeout after one completed candidate returns that candidate.
- Simulated timeout before any candidate returns safe fallback and logs reason.

### 0.5 Lockup Reproduction Scenario

Purpose: make the lockup class testable without replaying a full manual game.

Build a headless stress scenario inspired by the observed chain:

- Commander FFA or multi-player Commander-like game.
- Large battlefield, around 40+ permanents.
- Spell-copy/token/resource deck state:
  - Storm-Kiln Artist or equivalent treasure-on-spell engine.
  - Expressive Iteration / Braingeyser / Magma Opus-like spell line if available.
  - Hall of Oracles-like sorcery-speed activated ability.
  - Several token-producing effects.
- Strategic AI at skill/depth 10.

Assertions:

- Search returns within configured timeout.
- Effective depth is reduced.
- Trace contains search-budget reason.
- No client/server responsiveness assumption; test only search method if possible.

If a full game test is too expensive, create a pure policy test plus an integration test that invokes advisory/priority calculation on a constructed board with a low timeout.

### 0.6 Manual Testing Defaults

Keep adaptive budget on by default for the strategic AI. For local manual runs, override the packaged defaults with JVM `-D` properties such as:

- `-Dxmage.ai.strategy.searchBudget.enabled=false`
- `-Dxmage.ai.strategy.searchBudget.minDepth=5`
- `-Dxmage.ai.strategy.searchBudget.largeBoardThreshold=45`
- `-Dxmage.ai.strategy.searchBudget.hugeBoardThreshold=65`
- `-Dxmage.ai.strategy.searchBudget.heapGuard.maxUsedRatio=0.90`

Manual smoke testing should start with tracing and monitor mode, then enable one small module group at a time.

Success criteria:

1. Long Commander games do not lock the server for minutes.
2. Thread dumps during heavy AI thinking show search exiting/evaluating, not unbounded `GameState.copy`.
3. Trace logs show when depth/budget was reduced.
4. Strategic choices remain plausible enough for manual testing.
5. Original Mad AI remains a valid control path.

## Current Baseline

Implemented or scaffolded modules:

| Module | Current role | Main gap |
| --- | --- | --- |
| `deck-strategy` | Applies deck profile/card role priors. | Needs more reliable generated roles and mechanic confidence. |
| `ffa-table-threat` | Estimates table danger. | Needs richer threat breakdown by player/card. |
| `ffa-target-priority` | Scores FFA targets. | Needs politics/mutual-threat context. |
| `target-quality` | Scores target polarity and target quality. | Needs better self-targeting and protection/removal distinctions. |
| `combat-pressure` | Values attacks, lethal pressure, attack triggers. | Needs full attack-trigger and overkill-aware combat bundles. |
| `defense-reserve` | Penalizes leaving too little defense. | Needs next-player/crack-back modeling and FFA turn-order pressure. |
| `commander-lifecycle` | Values commander deployment, tax, protection, loss. | Needs “play something else instead” opportunity-cost scoring. |
| `phase-strategy` | Adds phase/step timing intent. | Needs phase-specific sub-strategies. |
| `resource-development` | Values ramp and mana source growth. | Needs sequencing with color access and hand plan. |
| `color-access` | Values colors unlocked for hand/commander. | Needs structured mana ability inspection beyond text fallback. |
| `hand-plan` | Values card access and hand conversion. | Needs draw odds and sequencing with hand quality. |
| `hand-quality` | Values actual hand-card keep quality and discard loss, using structured dynamic power/toughness values plus bounded future-potential floors. | Needs trace calibration for future-potential floors and broader cost/zone opportunity modeling. |
| `interaction-timing` | Rewards stack/interaction timing. | Needs threat windows and “must answer” classification. |
| `sacrifice-and-cost` | Scores sacrifice/cost patterns. | Needs payoff/fodder/outlet context. |
| `search-and-tutor` | Values search effects generically. | Needs target quality for what the deck can find. |
| `token-swarm` | Recognizes token pressure. | Needs board-wide go-wide payoffs and vulnerability. |
| `combo-progress` | Gives generic combo/resource progress credit, including source-preserving repeatable-loop progress toward visible combat lethal. | Needs broader goal types and 20+ loop stress characterization. |
| `win-closure` | Reduces overkill and encourages closing. | Needs exact lethal packages and “stop after enough” behavior. |
| `political-memory` | Placeholder. | Needs data model and trace inputs. |
| `alternate-loss` | Tracks non-life loss pressure. | Needs more alternate win/loss conditions. |

Current action-surface support:

- Strategic-only cast variants can expose preselected cost plans as candidate actions while preserving the original Mad AI control path.
- Initial optional-additional coverage includes binary buyback and bounded replicate counts. Buyback variants advertise source preservation; additional repeatable variants such as squad and multikicker still need provider-specific count generation.
- The Storm-Kiln Artist + Haze of Rage benchmark now has active two-loop and ten-loop strategic tests plus scorer coverage for loop preservation, progress, resource-goal-met, and loop-over-goal. The twenty-loop test remains an ignored stress placeholder until runtime is characterized.

## Roadmap Principles

1. Preserve the control AI. Do not change original Mad AI behavior except tracing, bug fixes, or shared utilities that do not alter decisions.
2. Build monitor-first. Every new module starts with raw signals and `.apply=false`.
3. Keep modules generic. Prefer “attack tax,” “sacrifice outlet,” “death payoff,” or “commander tax opportunity cost” over card-specific rules.
4. Explain every modifier. Each contribution needs a stable label and human-readable detail.
5. Prefer structured engine data. Use text matching only as a transitional fallback.
6. Keep scoring modest. If a module needs a huge modifier, first check whether the base simulation or game-state model is wrong.
7. Test with small headless unit tests first, then Commander trace runs, then applied human evaluation.

## Priority 1: Decision Observability

### 1.1 Decision Feature Snapshots

Purpose: make score swings explainable without opening a full game replay.

Add a compact per-candidate/per-chosen snapshot to traces:

| Field | Why |
| --- | --- |
| `lifeByPlayer` | Detect lethal/race state shifts. |
| `boardPowerByPlayer` | Explain combat and threat shifts. |
| `manaSourcesByPlayer` | Explain development swings. |
| `colorAccessByPlayer` | Explain special land/fixing value. |
| `handSizeByPlayer` | Explain resource pressure. |
| `topThreats` | Show which permanents drove board score. |
| `commanderState` | Show zone, tax, and commander board presence. |
| `phaseIntent` | Distinguish real game step from strategic timing label. |

Implementation notes:

- Implemented `AiDecisionFeatureSnapshot` near the trace classes.
- Candidate/chosen trace rows now write `startFeatureSnapshot` and `finalFeatureSnapshot` when tracing is enabled.
- Snapshots are bounded: per-player summaries plus top 3 threats per player/global.
- Local timeline tooling can surface selected-decision snapshots and candidate feature deltas while remaining compatible with older logs.

Suggested labels:

- `snapshot:player-threat`
- `snapshot:commander-state`
- `snapshot:color-access`

Headless checks:

- Unit test snapshot building on a small synthetic game if practical.
- Script smoke test: timeline generator handles old logs without snapshots and new logs with snapshots. Old-log compatibility was checked against existing `logs/ai-decisions/dev-companion` traces.

### 1.2 Module Delta Report

Purpose: show what applied modules would have changed without producing huge files.

Extend local monitor reporting to include:

| Report section | Purpose |
| --- | --- |
| `Top Would-Change By Module` | Which modules most often move decisions. |
| `Largest Raw But Unapplied Signals` | Find modules ready for manual application or calibration. |
| `Repeated Low-Ranked Useful Actions` | Detect undervalued card classes such as sacrifice outlets or special lands. |
| `Decision Swing Timeline` | Link score jumps to chosen action and feature snapshot changes. |

Keep reports aggregate-first with `--split-games` for historical runs.

## Priority 2: Combat And Win Closure

Combat is where many visible mistakes appear, but avoid treating it as only attack/defend. Combat modules should interact with FFA threat, turn order, commander protection, and win closure.

### 2.1 Exact Lethal Package Module

Purpose: choose the smallest sufficient attack/action package that kills or meaningfully pressures an opponent.

Current problem:

- AI can overcommit into an already lethal attack.
- AI may keep evaluating additional moves after lethal is achieved.
- Infinite or near-infinite resource loops need a goal, not endless continuation.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `win-closure:exact-lethal` | positive | Candidate kills with low excess commitment. |
| `win-closure:overkill-commitment` | negative | Candidate commits materially more attackers/resources than needed. |
| `win-closure:stop-after-goal` | positive | Candidate reaches lethal/resource goal and avoids extra loop steps. |
| `win-closure:missed-lethal` | diagnostic | Another legal candidate had lethal but chosen did not. |

Implementation notes:

- Reuse combat legal state from engine; do not invent legality.
- Compute damage bundles after known static abilities and known attack triggers.
- In FFA, killing one opponent is not always worth dying next turn, so feed results into `defense-reserve`.
- Keep exact-lethal score bounded. It should break ties and correct overkill, not override impossible combat.

Tests:

- Headless lethal with one opponent at low life.
- Overkill test where two attackers kill but all attackers expose player.
- Commander FFA test where killing one opponent leaves player dead to next opponent.

### 2.2 Attack Trigger Projection V2

Purpose: treat “when attacks” triggers as part of the declared attackers optimization.

Current problem:

- “Whenever this attacks” is distinct from “is attacking.”
- The advisor missed Dragon attack-trigger effects such as granting double strike to attacking Dragons.
- Attackers put onto battlefield attacking should not trigger “when attacks.”

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `combat-pressure:attack-trigger-damage` | positive | Declaring attackers triggers extra damage/power/evasion. |
| `combat-pressure:attack-trigger-token` | positive | Attack produces bodies or attacking tokens. |
| `combat-pressure:attack-trigger-card-resource` | positive | Attack draws/cards/treasures/resources. |
| `combat-pressure:attack-trigger-risk` | negative | Attack trigger has cost/sacrifice/discard downside. |

Implementation notes:

- Continue using `AttackTriggerProjection`, but split trigger discovery from scoring.
- Add structured detection for triggered abilities where source is declared as attacker.
- Avoid text-only “attacks” detection where possible; inspect `TriggeredAbility` event type if available.
- Ensure companion and actual AI use the same projected trigger utility.

Tests:

- Creature with “whenever this attacks” trigger.
- Creature put onto battlefield attacking should not trigger.
- Team/tribal attack trigger that modifies other attackers.

### 2.3 Block Quality And Illegal Block Advisor Fixes

Purpose: prevent the companion/AI from recommending blocks against attackers aimed at another player and improve block trade quality.

Current problem:

- Companion suggested blocking a creature attacking another opponent.
- It recommended trading a 4/3 flyer into a 4/8 attacker where the subjective value was poor.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `block-quality:illegal-defender` | diagnostic/negative | Block candidate does not defend this player/planeswalker/battle. |
| `block-quality:bad-trade` | negative | Block loses higher future value for low prevented damage. |
| `block-quality:preserve-evasion` | negative | Block spends scarce flyer/reach body unnecessarily. |
| `block-quality:lethal-prevention` | positive | Block prevents lethal or commander damage loss. |
| `block-quality:profitable-trade` | positive | Block trades up or preserves important life total. |

Implementation notes:

- First ensure action surface only includes legal blocks for the defending player.
- Add a block-quality module or extend combat module only after legality is verified.
- Score prevented damage against life total, commander damage, and future blocker scarcity.

Tests:

- FFA attack at another opponent should not be blockable by us.
- Bad chump/trade vs nonlethal damage should be penalized.
- Lethal prevention should override creature preservation.

### 2.4 Crack-Back And Defense Reserve V2

Purpose: leave enough defense for the next full turn cycle, especially in FFA.

Current problem:

- Current defense reserve is too coarse.
- It does not fully account for turn order, next player, evasive attackers, tapped blockers, or go-wide boards.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `defense-reserve:next-player-risk` | negative | Next player can attack for serious damage. |
| `defense-reserve:evasion-gap` | negative | Player lacks blockers for flying/menace/trample/etc. |
| `defense-reserve:token-swarm-risk` | negative | Opponent can punish low blocker count. |
| `defense-reserve:shield-kept` | positive | Candidate preserves enough blockers. |

Implementation notes:

- Estimate only visible battlefield threats.
- Start with next active opponent, then expand to full turn cycle.
- Use “available blockers after candidate” rather than current board only.

Tests:

- FFA board where attacking all exposes lethal from next player.
- Vigilance attacker should not reduce defense reserve.
- Evasive opponent threat should matter even if raw blocker count is high.

## Priority 3: Commander-Centric Strategy

### 3.1 Commander Opportunity Cost

Purpose: stop repeatedly recasting an expensive commander when hand plays are better.

Current problem:

- AI sometimes spends 12+ mana on commander recast instead of developing hand.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `commander-lifecycle:opportunity-cost` | negative | Commander recast consumes most mana while hand has good alternatives. |
| `commander-lifecycle:deck-engine-needed` | positive | Commander is central to deck plan and tax is acceptable. |
| `commander-lifecycle:wait-for-protection` | negative | Commander recast into obvious removal/combat risk without protection. |
| `commander-lifecycle:tax-recovery` | positive | Candidate helps pay commander tax next turn. |

Implementation notes:

- Use deck profile commander synergy: commander roles/mechanics aligned with deck roles increase value.
- Compare commander action against best noncommander hand candidate in same phase.
- If commander is the only engine/payoff and hand is weak, reduce penalty.

Tests:

- Commander tax 10+ with multiple castable hand cards should avoid commander.
- Low-tax commander with deck-aligned role should still deploy.
- Commander recast with protection available should be better than naked recast.

### 3.2 Commander Protection Windows

Purpose: protect commanders at the right time, not just when targeted.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `commander-lifecycle:hold-protection` | positive/negative | Preserve protection for commander if risk is high. |
| `commander-lifecycle:protect-before-combat` | positive | Protection enables safe attack/block or avoids lethal tax. |
| `commander-lifecycle:overprotect` | negative | Uses protection on low-risk commander state. |

Implementation notes:

- Integrate with interaction timing and target quality.
- Detect protection cards from role classifier and outcomes.
- Avoid holding all interaction forever; protection competes with real threats.

## Priority 4: Resource, Hand, And Hidden Information

### 4.1 Hand Quality And Draw Odds

Purpose: value card draw and card selection based on what the deck/hand needs.

Current problem:

- Card draw can look weak because hidden information has no concrete board result.
- The AI needs odds of drawing useful categories, not LLM card knowledge.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `hand-plan:draw-needed-role` | positive | Draw likely finds missing deck role. |
| `hand-plan:selection-needed-color` | positive | Scry/tutor/selection can find missing color. |
| `hand-plan:low-hand-refill` | positive | Hand is low and deck profile wants resources. |
| `hand-plan:draw-before-action` | positive | Draw/selection should happen before spending flexible mana. |
| `hand-plan:draw-too-slow` | negative | Draw action is low impact under lethal pressure. |

Implementation notes:

- Use library/deck role distribution when known; otherwise use starting deck profile priors.
- Estimate probability of drawing at least one needed role in N cards.
- Feed color-access gaps into draw valuation.
- Keep hidden-info scores modest and trace probability details.

Tests:

- Low hand, stable board: draw gets positive signal.
- Under lethal pressure: draw-only action does not override removal/blocking.
- Missing land/color: selection/tutor gets role-specific credit.

### 4.2 Mana Sequencing And Spend Plan

Purpose: prefer actions that use mana efficiently without blocking better plays.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `resource-development:sequence-unlocks` | positive | Mana action unlocks additional same-turn play. |
| `resource-development:wastes-critical-mana` | negative | Action spends mana needed for higher-value hand/interaction. |
| `resource-development:hold-open-interaction` | positive/negative | Holding mana is correct if live interaction exists. |
| `color-access:future-unlock` | positive | Color source unlocks likely next-turn hand/commander plan. |

Implementation notes:

- Build a small “available mana after candidate” estimate.
- Use action list, not full planning search at first.
- Coordinate with phase strategy: pre-draw should avoid spending flexible mana unless urgent.

Tests:

- Play land/fixer before spell if it unlocks spell.
- Avoid spending only blue source before holding counter/removal if opponent turn is imminent.

### 4.3 Tutor/Search Target Quality

Purpose: value search by what it can plausibly find.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `search-and-tutor:finds-missing-role` | positive | Search can find needed removal/ramp/protection/payoff. |
| `search-and-tutor:finds-color` | positive | Land search fixes missing colors. |
| `search-and-tutor:combo-piece` | positive | Search can find deck-profile aligned synergy piece. |
| `search-and-tutor:low-impact` | negative | Search cannot materially improve current plan. |

Implementation notes:

- Start with broad search classes: land, basic land, creature, artifact, enchantment, any card.
- Use known library contents if visible in simulation/test; otherwise deck profile priors.
- Avoid card-specific tutor targets.

## Priority 5: Synergy And Deck Identity

### 5.1 Sacrifice Engine V2

Purpose: stop treating sacrifice outlets as bad just because sacrificing a creature has immediate cost.

Current problem:

- Woe Strider/Yahenni-like actions appeared 100% low-ranked.
- Sacrifice decks need outlet + fodder + payoff context.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `sacrifice-and-cost:outlet-online` | positive | Outlet is valuable with fodder/payoff present. |
| `sacrifice-and-cost:fodder-available` | positive | Candidate uses expendable or recursive material. |
| `sacrifice-and-cost:payoff-triggered` | positive | Sacrifice triggers death/drain/counter/card payoff. |
| `sacrifice-and-cost:spends-engine-piece` | negative | Candidate sacrifices commander/key payoff without strong reason. |
| `sacrifice-and-cost:no-fodder` | negative | Sacrifice cost consumes scarce board. |

Implementation notes:

- Use roles: `SACRIFICE_OUTLET`, `SACRIFICE_FODDER`, `SACRIFICE_FODDER_PROVIDER`, death payoffs, graveyard recursion.
- Separate card role from action role. An outlet on board may be valuable even if activating now is wrong.
- Add board-context scoring, not only action-text scoring.

Tests:

- Sac outlet with no fodder: low/neutral.
- Sac outlet with token fodder and payoff: positive.
- Sacrificing commander/key creature without payoff: negative.

### 5.2 Counters And Tall Threat Scaling

Purpose: value +1/+1 counter decks correctly without treating large numbers as suspicious by default.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `counter-synergy:maker-payoff-linked` | positive | Candidate adds counters when payoffs exist. |
| `counter-synergy:tall-threat` | positive | Tall creature creates lethal/commander pressure. |
| `counter-synergy:win-more` | negative | More counters do not improve clock/survival. |
| `counter-synergy:exposed-tall-threat` | negative | Piles resources into removable, unprotected target. |

Implementation notes:

- This can be a new module or an expansion of deck-strategy/combo-progress.
- Use deck profile and current board, not hard-coded Quandrix assumptions.
- Distinguish legitimate deck strategy from score explosion by tracking whether counters change lethal clock.

Tests:

- Hardened Scales-like payoff with counter maker gets positive signal.
- Huge creature with no evasion into chump board gets lower value.
- Counter spell that creates lethal next turn gets positive signal.

### 5.3 Tribal And Mechanic Synergy

Purpose: use classifier-generated mechanics safely.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `deck-strategy:mechanic-aligned` | positive | Candidate matches high-confidence deck mechanic. |
| `deck-strategy:tribal-payoff` | positive | Candidate improves a relevant subtype cluster. |
| `deck-strategy:mechanic-noise` | diagnostic | Mechanic appears but low confidence or not actionable. |

Implementation notes:

- Keep generated mechanics monitor-only until aggregate reports show positive effect.
- Apply only high-confidence, deck-level repeated mechanics.
- Avoid scoring every matching tag; cap per mechanic family.

## Priority 6: Interaction And Targeting

### 6.1 Must-Answer Threat Detection

Purpose: prioritize removal/counters/protection against threats that will actually decide the game.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `interaction-timing:must-answer` | positive | Target threatens lethal, lock, combo, or commander engine. |
| `interaction-timing:answer-window` | positive | Now is the last good chance to answer. |
| `interaction-timing:premature-answer` | negative | Uses answer before threat matters. |
| `target-quality:wrong-threat` | negative | Targets low-impact permanent while larger threat exists. |

Implementation notes:

- Start with visible lethal and massive board swing threats.
- Include stack objects separately from permanents.
- Feed from `threat-projection` rather than duplicating threat math.

Tests:

- Removal targets lethal attacker/payoff over random creature.
- Counter spell uses stack window for high-impact spell.
- Does not spend removal on harmless utility permanent under no pressure.

### 6.2 Self-Targeting And Protection Quality

Purpose: handle actions like using Path to Exile on own creature or protection spells on friendly permanents.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `target-quality:self-ramp-emergency` | positive | Self-removal is correct to ramp/fix under specific need. |
| `target-quality:self-harm` | negative | Harmful self-target without compensation. |
| `target-quality:protect-key-piece` | positive | Protects commander/combo/payoff. |
| `target-quality:protect-low-value` | negative | Protects expendable object. |

Implementation notes:

- Use outcomes plus target controller.
- For self-hostile actions, require explicit compensation: ramp, death payoff, save from exile, trigger benefit.
- Coordinate with sacrifice and commander modules.

## Priority 7: FFA Politics And Table Memory

### 7.1 Political Memory Data Model

Purpose: remember who is attacking whom and who is the table threat.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `political-memory:leader-pressure` | positive | Attacking/removing current leader. |
| `political-memory:avoid-weak-player` | negative | Picks on weak player while leader is unchecked. |
| `political-memory:revenge-risk` | negative | Provokes next player without gain. |
| `political-memory:mutual-threat` | positive | Supports attacking shared table threat. |

Implementation notes:

- Start trace-only: no direct application until data looks sane.
- Track only compact facts: damage dealt, kills attempted, largest board, current leader by feature snapshot.
- Avoid modeling player personalities.

Tests:

- Leader with strongest board/life advantage becomes target priority.
- Weak nearly-dead player is not over-targeted when leader is threatening.

### 7.2 Threat Projection V2

Purpose: explain “who is winning” and “which card is the threat” over time.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `threat-projection:player-leader` | diagnostic | Current best/worst player estimate. |
| `threat-projection:card-threat` | diagnostic/negative | Permanent driving opponent score. |
| `threat-projection:imminent-lethal` | negative | Opponent can kill soon. |
| `threat-projection:engine-online` | negative | Opponent has repeatable value engine. |

Implementation notes:

- Add named threat reasons for timeline visualization.
- Keep top N threats only.
- Connect to interaction-target quality.

## Priority 8: Combo And Alternate Win/Loss

### 8.1 Goal-Directed Combo Progress

Purpose: recognize finite progress toward a win/resource goal without infinite loop behavior.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `combo-progress:piece-online` | positive | Candidate assembles visible synergy pieces. |
| `combo-progress:resource-goal-met` | positive | Candidate reaches enough mana/damage/tokens/cards. |
| `combo-progress:repeatable-spell-preserved` | positive | Candidate keeps a repeatable spell line available, such as a buyback spell. |
| `combo-progress:repeatable-spell-spent` | negative | Candidate spends a repeatable spell before the visible goal is met. |
| `combo-progress:loop-over-goal` | negative | Candidate keeps repeating after a visible combat goal is already available. |
| `combo-progress:loop-no-progress` | negative | Repeats without improving goal. |
| `combo-progress:interruptible-risk` | negative | Combo line is exposed to visible interaction. |

Implementation notes:

- Define goals: lethal damage, win trigger, enough mana to cast hand/commander, enough tokens/counters to threaten lethal.
- Add loop/progress counters to traces when simulation repeats similar actions.
- Avoid claiming infinite; instead cap at “goal met.”

Tests:

- Biovisionary + kicked Rite of Replication win scenario.
- Resource loop stops once enough mana/damage is achieved.
- Repeated non-progress action is penalized.

### 8.2 Alternate Win/Loss Conditions

Purpose: handle poison, commander damage, mill, “you win the game,” “you lose the game,” and special counters.

Signals:

| Label | Direction | Meaning |
| --- | --- | --- |
| `alternate-loss:poison-risk` | negative | Poison/proliferate clock matters. |
| `alternate-loss:commander-damage-risk` | negative | Commander damage lethal clock. |
| `alternate-loss:library-risk` | negative | Mill/draw-from-empty danger. |
| `alternate-loss:win-condition-online` | positive | Own alternate win condition is close. |

Implementation notes:

- Use structured game/player state where available.
- Add card text fallback for “win the game” only as diagnostic initially.

## Priority 9: Card Classifier And Deck Profile Integration

### 9.1 Classifier Confidence Pipeline

Purpose: use generated classifier work safely.

Tasks:

1. Store confidence per role/mechanic.
2. Separate source of label: static rule, generated classifier, deck-profile inference, runtime observation.
3. In `deck-strategy`, apply only stable high-confidence roles by default.
4. Keep generated mechanics trace-only until validated.

Trace fields:

| Field | Meaning |
| --- | --- |
| `roleSource` | static/generated/profile/runtime. |
| `roleConfidence` | 0..1 or low/medium/high. |
| `deckMechanicDensity` | How strongly mechanic appears in deck. |

### 9.2 Dynamic Clustering

Purpose: identify deck themes without hand-coding every archetype.

Features:

- Role counts.
- Mechanic/subtype clusters.
- Mana curve and color intensity.
- Zone interaction: graveyard, exile, command zone.
- Resource patterns: tokens, counters, sacrifice, artifacts, spellslinger, lifegain.

Output:

- `DeckStrategyProfile` should expose compact archetype weights.
- Modules use weights as priors, not absolute rules.

## Suggested Implementation Order

Use this order unless manual logs expose a more urgent bug:

1. Strategic AI search stabilization.
2. Decision feature snapshots and timeline/report upgrades.
3. Exact lethal/overkill package scoring.
4. Block legality/quality and advisor mismatch fixes.
5. Attack trigger projection V2.
6. Commander opportunity cost.
7. Hand quality and draw odds.
8. Sacrifice engine V2.
9. Crack-back/defense reserve V2.
10. Tutor/search target quality.
11. Threat projection V2 with top card threats.
12. Political memory trace-only model.
13. Combo progress and alternate win/loss expansion.
14. Classifier confidence pipeline and dynamic clustering.

## Manual Evaluation Plan

Start with monitor mode:

Enable `xmage.ai.trace=true` and `xmage.ai.strategy.traceOnly=true`.

Then apply one small group at a time:

Apply one small group by setting the relevant `.apply` properties, for example resource development plus color access, combat pressure plus defense reserve, or commander lifecycle plus hand quality.

When local reporting tools are available, generate aggregate and per-game summaries from the JSONL trace directory.

Evaluate by asking:

1. Did the strategic choice change from the base choice?
2. Was the changed choice legal and human-plausible?
3. Which module caused it?
4. Was the modifier modest or overpowering?
5. Did the trace explain the decision without relying on outside card knowledge?
6. Does this still preserve the original Mad AI as control?

## Definition Of Done For A New Module

A module is ready for manual application when:

1. It has `enabled`, `apply`, and `maxScoreModifier` properties.
2. It is registered in `AiScoreModuleId`, `AiScoreModuleRegistry`, and `AiScoringEngineFactory`.
3. It has stable contribution labels and useful detail text.
4. It has at least one focused headless test for pure helpers or a small game scenario.
5. `mvn -pl Mage.Server.Plugins/Mage.Player.AI.MA -am -DskipTests compile` passes.
6. New properties are documented in `Mage.Server/release/config/ai-strategy.properties`.
7. Monitor logs show nonzero raw signals before enabling `.apply=true`.
8. The architecture or roadmap docs are updated.

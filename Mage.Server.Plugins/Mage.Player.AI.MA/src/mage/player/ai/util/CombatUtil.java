package mage.player.ai.util;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.CombatDamageStep;
import mage.game.turn.EndOfCombatStep;
import mage.game.turn.Step;
import mage.player.ai.StrategicRoleEvaluator;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Base helper methods for combat.
 *
 * @author noxx
 */
public final class CombatUtil {

    private static final List<Permanent> emptyList = Collections.unmodifiableList(new ArrayList<>());

    private static final Logger log = Logger.getLogger(CombatUtil.class);

    private CombatUtil() {
    }

    public static List<Permanent> canKillOpponent(Game game, List<Permanent> attackersList, List<Permanent> blockersList,
                                                  Player defender) {
        List<Permanent> blockableAttackers = new ArrayList<>(attackersList);
        List<Permanent> unblockableAttackers = new ArrayList<>();
        for (Permanent attacker : attackersList) {
            if (!canBeBlocked(game, attacker, blockersList)) {
                unblockableAttackers.add(attacker);
                blockableAttackers.remove(attacker);
            }
        }

        sortByPower(blockableAttackers, false); // most powerfull go to first

        // imagine that most powerful will be blocked as 1-vs-1
        List<Permanent> attackersThatWontBeBlocked = new ArrayList<>(blockableAttackers);
        for (int i = 0; (i < blockersList.size() && i < blockableAttackers.size()); i++) {
            attackersThatWontBeBlocked.remove(blockableAttackers.get(i));
        }
        attackersThatWontBeBlocked.addAll(unblockableAttackers);

        // now count if it is possible to win the game by this attack using unblockable attackers and
        // those attackers that won't be blocked for sure (as player will block other creatures)
        if (sumDamage(attackersThatWontBeBlocked, defender) >= defender.getLife() && defender.isLifeTotalCanChange()
                && defender.canLose(game) && defender.getLife() > 0) {
            blockableAttackers.addAll(unblockableAttackers);
            return blockableAttackers;
        }

        if (sumPoisonDamage(attackersThatWontBeBlocked, defender) >= 10 - defender.getCountersCount(CounterType.POISON)) {
            blockableAttackers.addAll(unblockableAttackers);
            return blockableAttackers;
        }

        return emptyList;
    }

    /**
     * Checks that attacker can be blocked by any blocker from the list.
     *
     * @param game         Game
     * @param attacker     Attacker to check
     * @param blockersList Blockers to try to block the attacker with.
     * @return true if attacker can be blocked by any blocker
     */
    public static boolean canBeBlocked(Game game, Permanent attacker, List<Permanent> blockersList) {
        for (Permanent blocker : blockersList) {
            if (blocker.canBlock(attacker.getId(), game)) {
                return true;
            }
        }
        return false;
    }

    public static void sortByPower(List<Permanent> permanents, final boolean ascending) {
        permanents.sort(Comparator.comparingInt(p -> p.getPower().getValue()));
        if (!ascending) {
            Collections.reverse(permanents);
        }
    }

    public static Permanent getWorstCreature(List<Permanent>... lists) {
        for (List<Permanent> list : lists) {
            if (!list.isEmpty()) {
                list.sort(Comparator.comparingInt(p -> p.getPower().getValue()));
                return list.get(0);
            }
        }
        return null;
    }

    public static void removeWorstCreature(Permanent permanent, List<Permanent>... lists) {
        for (List<Permanent> list : lists) {
            if (!list.isEmpty()) {
                list.remove(permanent);
            }
        }
    }

    private static int sumDamage(List<Permanent> attackersThatWontBeBlocked, Player defender) {
        int damage = 0;
        for (Permanent attacker : attackersThatWontBeBlocked) {
            if (!attacker.getAbilities().contains(InfectAbility.getInstance())) {
                damage += attacker.getPower().getValue();
                if (attacker.getAbilities().contains(DoubleStrikeAbility.getInstance())) {
                    damage += attacker.getPower().getValue();
                }
            }
        }
        return damage;
    }

    private static int sumPoisonDamage(List<Permanent> attackersThatWontBeBlocked, Player defender) {
        int damage = 0;
        for (Permanent attacker : attackersThatWontBeBlocked) {
            if (attacker.getAbilities().contains(InfectAbility.getInstance())) {
                damage += attacker.getPower().getValue();
                if (attacker.getAbilities().contains(DoubleStrikeAbility.getInstance())) {
                    damage += attacker.getPower().getValue();
                }
            }
        }
        return damage;
    }

    public static void handleExalted() {
    }

    /**
     * Determines what blockers from the list can block the attacker
     *
     * @param game         Game
     * @param attacker     Attacker to check
     * @param blockersList Blockers to try to block the attacker with.
     * @return true if attacker can be blocked by any blocker
     */
    public static List<Permanent> getPossibleBlockers(Game game, Permanent attacker, List<Permanent> blockersList) {
        List<Permanent> canBlock = new ArrayList<>();
        for (Permanent blocker : blockersList) {
            if (blocker.canBlock(attacker.getId(), game)) {
                canBlock.add(blocker);
            }
        }
        return canBlock;
    }

    /**
     * AI related code, find better block combination for attackers
     */
    public static CombatInfo blockWithGoodTrade2(Game game, List<Permanent> attackers, List<Permanent> blockers) {
        UUID attackerId = game.getCombat().getAttackingPlayerId();
        UUID defenderId = game.getCombat().getDefenders().iterator().next();
        if (attackerId == null || defenderId == null) {
            return new CombatInfo();
        }

        // Determine strategic role for role-aware blocking decisions
        Player defendingPlayer = game.getPlayer(defenderId);
        Player attackingPlayer = game.getPlayer(attackerId);
        int strategicRole = StrategicRoleEvaluator.ROLE_FLEXIBLE;
        if (defendingPlayer != null && attackingPlayer != null) {
            strategicRole = StrategicRoleEvaluator.determineRole(defendingPlayer, attackingPlayer, game);
        }

        // TODO: implement full game simulations of all possible combinations (e.g. multiblockers support)

        CombatInfo combatInfo = new CombatInfo();
        for (Permanent attacker : attackers) {
            // Check if attacker has deathtouch - this affects blocking strategy significantly
            boolean attackerHasDeathtouch = attacker.getAbilities(game).containsKey(DeathtouchAbility.getInstance().getId());

            // simple combat simulation (1 vs 1)
            List<Permanent> allBlockers = getPossibleBlockers(game, attacker, blockers);
            List<SurviveInfo> blockerStats = getBlockersThatWillSurvive2(game, attackerId, defenderId, attacker, allBlockers);
            Map<Permanent, Integer> blockingDiffScore = new HashMap<>();
            Map<Permanent, Integer> nonBlockingDiffScore = new HashMap<>();
            blockerStats.forEach(s -> {
                blockingDiffScore.put(s.getBlocker(), s.getDiffBlockingScore());
                nonBlockingDiffScore.put(s.getBlocker(), s.getDiffNonblockingScore());
            });

            // split blockers by usage priority
            List<Permanent> survivedAndKillBlocker = new ArrayList<>();  // Blocker survives AND kills attacker (best)
            List<Permanent> survivedBlockers = new ArrayList<>();         // Blocker survives (may not kill attacker)
            List<Permanent> tradedBlockers = new ArrayList<>();           // Both die (mutual trade, acceptable)
            List<Permanent> chumpBlockers = new ArrayList<>();            // Only blocker dies (chump block)
            blockerStats.forEach(stats -> {
                if (stats.isAttackerDied() && !stats.isBlockerDied()) {
                    survivedAndKillBlocker.add(stats.getBlocker());
                } else if (!stats.isBlockerDied()) {
                    survivedBlockers.add(stats.getBlocker());
                } else if (stats.isAttackerDied()) {
                    // Both die - mutual trade
                    tradedBlockers.add(stats.getBlocker());
                } else {
                    // Only blocker dies - chump block
                    chumpBlockers.add(stats.getBlocker());
                }
            });
            // Combine for backwards compatibility (diedBlockers = tradedBlockers + chumpBlockers)
            List<Permanent> diedBlockers = new ArrayList<>(tradedBlockers);
            diedBlockers.addAll(chumpBlockers);

            int blockedCount = 0;

            // Special handling for deathtouch attackers:
            // Against deathtouch, ALL blockers die (regardless of toughness), so survivedAndKillBlocker
            // and survivedBlockers should be empty. We must use the LOWEST VALUE creature if we block at all.
            // The trade is only worth it if the blocker's value is less than or similar to the attacker's value,
            // or if not blocking would lose the game.
            if (attackerHasDeathtouch && survivedAndKillBlocker.isEmpty() && survivedBlockers.isEmpty()) {
                // All blockers will die due to deathtouch
                // Find the blocker with the lowest power that can still kill the attacker
                // (any blocker can kill a deathtouch creature since we're trading anyway)
                Permanent bestBlocker = null;
                int bestScore = Integer.MIN_VALUE;

                for (Permanent b : diedBlockers) {
                    int diffBlockingScore = blockingDiffScore.getOrDefault(b, 0);
                    int diffNonBlockingScore = nonBlockingDiffScore.getOrDefault(b, 0);

                    // Only consider blocking if it improves the game state or prevents loss
                    if (diffBlockingScore >= diffNonBlockingScore) {
                        // Prefer the lowest power creature (smallest sacrifice)
                        // Use negative power as score so lower power = higher "score"
                        int score = -b.getPower().getValue();
                        if (bestBlocker == null || score > bestScore) {
                            bestBlocker = b;
                            bestScore = score;
                        }
                    }
                }

                if (bestBlocker != null) {
                    combatInfo.addPair(attacker, bestBlocker);
                    removeWorstCreature(bestBlocker, blockers, diedBlockers);
                    blockedCount++;
                }
            } else {
                // Normal blocking logic (non-deathtouch or some blockers survive)
                // find good blocker that survives
                Permanent blocker = getWorstCreature(survivedAndKillBlocker, survivedBlockers);
                if (blocker != null) {
                    combatInfo.addPair(attacker, blocker);
                    removeWorstCreature(blocker, blockers, survivedAndKillBlocker, survivedBlockers);
                    blockedCount++;
                }

                // If no survivor, try 1-for-1 trade (mutual destruction) before group blocking
                // This is often preferable to using 2 blockers for one attacker
                if (blocker == null && !tradedBlockers.isEmpty()) {
                    blocker = getWorstCreature(tradedBlockers);
                    if (blocker != null) {
                        combatInfo.addPair(attacker, blocker);
                        removeWorstCreature(blocker, blockers, tradedBlockers, diedBlockers);
                        blockedCount++;
                    }
                }

                // Try group blocking (2 blockers) only if no single blocker can kill the attacker
                // Skip for trample attackers (excess damage goes through anyway)
                boolean attackerHasTrample = attacker.getAbilities(game).containsKey(TrampleAbility.getInstance().getId());
                if (blocker == null && !attackerHasTrample && allBlockers.size() >= 2) {
                    // Try to find a 2-blocker combination that kills the attacker with at least one survivor
                    GroupBlockResult groupBlock = findBestGroupBlock(game, attacker, allBlockers);
                    if (groupBlock != null) {
                        combatInfo.addPair(attacker, groupBlock.blocker1);
                        combatInfo.addPair(attacker, groupBlock.blocker2);
                        blockers.remove(groupBlock.blocker1);
                        blockers.remove(groupBlock.blocker2);
                        blockedCount += 2;
                    }
                }

                // find good sacrifices (chump blocks also supported due bad game score on loose)
                // Strategic role affects willingness to chump block:
                // - Beatdown: Preserve creatures for attacking, only chump if it prevents lethal
                // - Control: More willing to chump block to preserve life total
                // - Flexible: Standard logic
                if (blocker == null && blockedCount == 0) {
                    blocker = getWorstCreature(diedBlockers);
                    if (blocker != null) {
                        int diffBlockingScore = blockingDiffScore.getOrDefault(blocker, 0);
                        int diffNonBlockingScore = nonBlockingDiffScore.getOrDefault(blocker, 0);

                        // Adjust threshold based on strategic role
                        boolean shouldChumpBlock;
                        if (strategicRole == StrategicRoleEvaluator.ROLE_BEATDOWN) {
                            // Beatdown: Only chump block if it significantly improves position
                            // (preserves creatures for attacking)
                            shouldChumpBlock = diffBlockingScore > diffNonBlockingScore + 20;
                        } else if (strategicRole == StrategicRoleEvaluator.ROLE_CONTROL) {
                            // Control: More willing to chump block to preserve life
                            shouldChumpBlock = diffBlockingScore >= diffNonBlockingScore - 10;
                        } else {
                            // Flexible: Standard logic
                            shouldChumpBlock = diffBlockingScore >= 0 || diffBlockingScore > diffNonBlockingScore;
                        }

                        if (shouldChumpBlock) {
                            // it's good - can sacrifice and get better game state, also protect from game loose
                            combatInfo.addPair(attacker, blocker);
                            removeWorstCreature(blocker, blockers, diedBlockers);
                            blockedCount++;
                        }
                    }
                }
            }

            // find blockers for restrictions
            while (true) {
                if (blockers.isEmpty()) {
                    break;
                }

                // TODO: add multiple use case support with min/max blockedBy conditional and other
                //   see all possible use cases in checkBlockRestrictions, checkBlockRequirementsAfter and checkBlockRestrictionsAfter

                // effects support: can't be blocked except by xxx or more creatures
                if (blockedCount > 0 && attacker.getMinBlockedBy() > blockedCount) {
                    // it already has 1 blocker (killer in best use case), so no needs in second killer
                    Permanent blocker = getWorstCreature(survivedBlockers, survivedAndKillBlocker, diedBlockers);
                    if (blocker != null) {
                        combatInfo.addPair(attacker, blocker);
                        removeWorstCreature(blocker, blockers, survivedBlockers, survivedAndKillBlocker, diedBlockers);
                        blockedCount++;
                        continue; // try to find next required blocker
                    } else {
                        // invalid configuration, must stop
                        break;
                    }
                }

                // no more active restrictions
                break;
            }

            // no more blockers to use
            if (blockers.isEmpty()) {
                break;
            }
        }

        return combatInfo;
    }

    /**
     * Game simulations to find all survived/killer blocker
     */
    private static List<SurviveInfo> getBlockersThatWillSurvive2(Game game, UUID attackerId, UUID defenderId, Permanent attacker, List<Permanent> possibleBlockers) {
        List<SurviveInfo> res = new ArrayList<>();
        for (Permanent blocker : possibleBlockers) {
            // TODO: enable willItSurviveSimulation and check stability
            SurviveInfo info = willItSurviveSimple(game, attackerId, defenderId, attacker, blocker);
            if (info == null) {
                continue;
            }
            info.setBlocker(blocker);
            res.add(info);
        }
        return res;
    }

    public static SurviveInfo willItSurviveSimulation(Game originalGame, UUID attackingPlayerId, UUID defendingPlayerId, Permanent attacker, Permanent blocker) {
        Game sim = originalGame.createSimulationForAI();
        if (blocker == null || attacker == null || sim.getPlayer(defendingPlayerId) == null) {
            return null;
        }

        // TODO: need code research, possible bugs in miss prepare code due real combat logic
        // TODO: bugged, miss combat.clear code (possible bugs - wrong blocker declare by AI on multiple options?)
        Combat combat = sim.getCombat();
        combat.setAttacker(attackingPlayerId);
        combat.setDefenders(sim);
        int startScore = GameStateEvaluator2.evaluate(defendingPlayerId, sim).getTotalScore();

        // real game simulation
        // TODO: need debug and testing, old code from 2012
        //   must have infinite/freeze protection (e.g. limit stack resolves)

        // declare
        sim.getPlayer(defendingPlayerId).declareBlocker(defendingPlayerId, blocker.getId(), attacker.getId(), sim);
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defendingPlayerId, defendingPlayerId));
        sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));

        // combat
        simulateStep(sim, new CombatDamageStep(true));
        simulateStep(sim, new CombatDamageStep(false));
        simulateStep(sim, new EndOfCombatStep());

        // after
        sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }

        int endBlockingScore = GameStateEvaluator2.evaluate(defendingPlayerId, sim).getTotalScore();
        int endNonBlockingScore = startScore; // TODO: implement
        return new SurviveInfo(
                !sim.getBattlefield().containsPermanent(attacker.getId()),
                !sim.getBattlefield().containsPermanent(blocker.getId()),
                endBlockingScore - startScore,
                endNonBlockingScore - startScore
        );
    }

    public static SurviveInfo willItSurviveSimple(Game originalGame, UUID attackingPlayerId, UUID defendingPlayerId, Permanent attacker, Permanent blocker) {
        Game sim = originalGame.createSimulationForAI();
        if (blocker == null || attacker == null || sim.getPlayer(defendingPlayerId) == null) {
            return null;
        }

        Combat combat = sim.getCombat();
        combat.setAttacker(attackingPlayerId);
        combat.setDefenders(sim);

        Game simNonBlocking = sim.copy();

        // attacker tapped before attack, it will add additional score to blocker, but it must be ignored
        // so blocker will block same creature with same score without penalty
        int startScore = GameStateEvaluator2.evaluate(defendingPlayerId, sim, false).getTotalScore();

        // fake combat simulation (simple damage simulation)
        Permanent simAttacker = sim.getPermanent(attacker.getId());
        Permanent simBlocker = sim.getPermanent(blocker.getId());
        if (simAttacker == null || simBlocker == null) {
            throw new IllegalArgumentException("Broken sim game, can't find attacker or blocker");
        }
        // don't ask about that hacks - just replace to real combat simulation someday (another hack but with full stack resolve)
        // first damage step
        simulateCombatDamage(sim, simBlocker, simAttacker, true);
        simulateCombatDamage(sim, simAttacker, simBlocker, true);
        simAttacker.applyDamage(sim);
        simBlocker.applyDamage(sim);
        sim.checkStateAndTriggered();
        sim.processAction();
        // second damage step
        if (sim.getPermanent(simBlocker.getId()) != null && sim.getPermanent(simAttacker.getId()) != null) {
            simulateCombatDamage(sim, simBlocker, simAttacker, false);
            simulateCombatDamage(sim, simAttacker, simBlocker, false);
            simAttacker.applyDamage(sim);
            simBlocker.applyDamage(sim);
            sim.checkStateAndTriggered();
            sim.processAction();
        }

        /* old manual PT compare
        if (attacker.getPower().getValue() >= blocker.getToughness().getValue()) {
            sim.getBattlefield().removePermanent(blocker.getId());
        }
        if (attacker.getToughness().getValue() <= blocker.getPower().getValue()) {
            sim.getBattlefield().removePermanent(attacker.getId());
        }
         */

        // fake non-block simulation
        simNonBlocking.getPlayer(defendingPlayerId).damage(
                attacker.getPower().getValue(),
                attacker.getId(),
                null,
                simNonBlocking,
                true,
                true
        );
        simNonBlocking.checkStateAndTriggered();
        simNonBlocking.processAction();

        int endBlockingScore = GameStateEvaluator2.evaluate(defendingPlayerId, sim, false).getTotalScore();
        int endNonBlockingScore = GameStateEvaluator2.evaluate(defendingPlayerId, simNonBlocking, false).getTotalScore();
        return new SurviveInfo(
                !sim.getBattlefield().containsPermanent(attacker.getId()),
                !sim.getBattlefield().containsPermanent(blocker.getId()),
                endBlockingScore - startScore,
                endNonBlockingScore - startScore
        );
    }

    private static void simulateCombatDamage(Game sim, Permanent fromCreature, Permanent toCreature, boolean isFirstDamageStep) {
        Ability fakeAbility = new SimpleStaticAbility(null);
        if (CombatGroup.dealsDamageThisStep(fromCreature, isFirstDamageStep, sim)) {
            fakeAbility.setSourceId(fromCreature.getId());
            fakeAbility.setControllerId(fromCreature.getControllerId());
            toCreature.damage(fromCreature.getPower().getValue(), fromCreature.getId(), fakeAbility, sim, true, true);
        }
    }

    private static void simulateStep(Game sim, Step step) {
        sim.getPhase().setStep(step);
        if (!step.skipStep(sim, sim.getActivePlayerId())) {
            step.beginStep(sim, sim.getActivePlayerId());
            // The following commented out call produces random freezes.
            //game.checkStateAndTriggered();
            while (!sim.getStack().isEmpty()) {
                sim.getStack().resolve(sim);
                sim.applyEffects();
            }
            step.endStep(sim, sim.getActivePlayerId());
        }
    }

    /**
     * Result of a group blocking analysis
     */
    private static class GroupBlockResult {
        final Permanent blocker1;
        final Permanent blocker2;

        GroupBlockResult(Permanent blocker1, Permanent blocker2) {
            this.blocker1 = blocker1;
            this.blocker2 = blocker2;
        }
    }

    /**
     * Find the best 2-blocker combination that can kill the attacker with at least one survivor.
     * Returns null if no good combination exists.
     */
    private static GroupBlockResult findBestGroupBlock(Game game, Permanent attacker, List<Permanent> possibleBlockers) {
        if (possibleBlockers.size() < 2) {
            return null;
        }

        int attackerPower = attacker.getPower().getValue();
        int attackerToughness = attacker.getToughness().getValue();

        GroupBlockResult bestResult = null;
        int bestScore = Integer.MIN_VALUE;

        // Try all 2-blocker combinations
        for (int i = 0; i < possibleBlockers.size(); i++) {
            for (int j = i + 1; j < possibleBlockers.size(); j++) {
                Permanent blocker1 = possibleBlockers.get(i);
                Permanent blocker2 = possibleBlockers.get(j);

                int blocker1Power = blocker1.getPower().getValue();
                int blocker2Power = blocker2.getPower().getValue();
                int blocker1Toughness = blocker1.getToughness().getValue();
                int blocker2Toughness = blocker2.getToughness().getValue();

                // Combined power must be enough to kill attacker
                int combinedPower = blocker1Power + blocker2Power;
                if (combinedPower < attackerToughness) {
                    continue; // Can't kill attacker
                }

                // At least one blocker must survive
                // Attacker assigns damage; it will typically kill the smaller blocker first
                // For simplicity, assume attacker kills the lower-toughness blocker
                Permanent lowerToughness = blocker1Toughness <= blocker2Toughness ? blocker1 : blocker2;
                Permanent higherToughness = blocker1Toughness > blocker2Toughness ? blocker1 : blocker2;

                int damageToLower = Math.min(attackerPower, lowerToughness.getToughness().getValue());
                int remainingDamage = attackerPower - damageToLower;

                boolean lowerSurvives = damageToLower < lowerToughness.getToughness().getValue();
                boolean higherSurvives = remainingDamage < higherToughness.getToughness().getValue();

                if (!lowerSurvives && !higherSurvives) {
                    continue; // Both blockers die - not a good trade
                }

                // Score this combination: prefer smaller blockers (less value sacrificed)
                // Use negative combined power as score (lower power = better)
                int score = -(blocker1Power + blocker2Power);

                // Bonus if both survive
                if (lowerSurvives && higherSurvives) {
                    score += 100;
                }

                if (bestResult == null || score > bestScore) {
                    bestResult = new GroupBlockResult(blocker1, blocker2);
                    bestScore = score;
                }
            }
        }

        return bestResult;
    }
}

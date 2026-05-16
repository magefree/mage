package mage.player.ai.util;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.keyword.InfectAbility;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.CombatDamageStep;
import mage.game.turn.EndOfCombatStep;
import mage.game.turn.Step;
import mage.player.ai.score.GameStateEvaluator2;
import mage.players.Player;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

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
        if (sumDamage(game, attackersThatWontBeBlocked) >= defender.getLife() && defender.isLifeTotalCanChange()
                && defender.canLose(game) && defender.getLife() > 0) {
            blockableAttackers.addAll(unblockableAttackers);
            return blockableAttackers;
        }

        if (sumPoisonDamage(game, attackersThatWontBeBlocked) >= 10 - defender.getCountersCount(CounterType.POISON)) {
            blockableAttackers.addAll(unblockableAttackers);
            return blockableAttackers;
        }

        return emptyList;
    }

    public static List<Permanent> canKillOpponentWithMinimalAttackers(Game game, List<Permanent> attackersList,
                                                                      List<Permanent> blockersList, Player defender) {
        List<Permanent> blockableAttackers = new ArrayList<>(attackersList);
        List<Permanent> unblockableAttackers = new ArrayList<>();
        for (Permanent attacker : attackersList) {
            if (!canBeBlocked(game, attacker, blockersList)) {
                unblockableAttackers.add(attacker);
                blockableAttackers.remove(attacker);
            }
        }

        sortByPower(blockableAttackers, false);
        List<Permanent> attackersThatWontBeBlocked = new ArrayList<>(blockableAttackers);
        for (int i = 0; (i < blockersList.size() && i < blockableAttackers.size()); i++) {
            attackersThatWontBeBlocked.remove(blockableAttackers.get(i));
        }
        attackersThatWontBeBlocked.addAll(unblockableAttackers);

        if (sumDamage(game, attackersThatWontBeBlocked) >= defender.getLife() && defender.isLifeTotalCanChange()
                && defender.canLose(game) && defender.getLife() > 0) {
            return getExecutableLethalAttackers(
                    game,
                    getMinimalLethalAttackers(game, blockableAttackers, unblockableAttackers,
                            blockersList.size(), defender.getLife(), false, true),
                    defender
            );
        }

        int poisonNeeded = 10 - defender.getCountersCount(CounterType.POISON);
        if (sumPoisonDamage(game, attackersThatWontBeBlocked) >= poisonNeeded) {
            return getExecutableLethalAttackers(
                    game,
                    getMinimalLethalAttackers(game, blockableAttackers, unblockableAttackers,
                            blockersList.size(), poisonNeeded, true, true),
                    defender
            );
        }

        return emptyList;
    }

    private static List<Permanent> getExecutableLethalAttackers(Game game, List<Permanent> lethalAttackers, Player defender) {
        if (lethalAttackers.isEmpty()) {
            return emptyList;
        }
        Player attackingPlayer = game.getPlayer(lethalAttackers.get(0).getControllerId());
        if (attackingPlayer == null || attackingPlayer.isHuman()) {
            return lethalAttackers;
        }

        Game sim = game.createSimulationForAI();
        Player simAttackingPlayer = sim.getPlayer(attackingPlayer.getId());
        if (simAttackingPlayer == null || sim.getPlayer(defender.getId()) == null) {
            return emptyList;
        }

        for (Permanent attacker : lethalAttackers) {
            Permanent simAttacker = sim.getPermanent(attacker.getId());
            if (simAttacker == null) {
                return emptyList;
            }
            simAttackingPlayer.declareAttacker(simAttacker.getId(), defender.getId(), sim, false);
            if (!sim.getCombat().getAttackers().contains(simAttacker.getId())) {
                return emptyList;
            }
        }
        return lethalAttackers;
    }

    public static boolean canWinByAttacking(Game game, Player attackingPlayer) {
        if (game == null || attackingPlayer == null || !game.isActivePlayer(attackingPlayer.getId())) {
            return false;
        }
        Set<UUID> usedAttackers = new HashSet<>();
        List<Player> defenders = game.getOpponents(attackingPlayer.getId(), true)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .filter(defender -> defender.isInGame() && !defender.hasLost())
                .collect(Collectors.toList());
        if (defenders.isEmpty()) {
            return false;
        }
        defenders.sort(Comparator.comparingInt(defender ->
                getLethalAttackersForDefender(game, attackingPlayer, defender, Collections.emptySet()).size()
        ));
        for (Player defender : defenders) {
            List<Permanent> lethalAttackers = getLethalAttackersForDefender(game, attackingPlayer, defender, usedAttackers);
            if (lethalAttackers.isEmpty()) {
                return false;
            }
            lethalAttackers.forEach(attacker -> usedAttackers.add(attacker.getId()));
        }
        return true;
    }

    private static List<Permanent> getLethalAttackersForDefender(Game game,
                                                                 Player attackingPlayer,
                                                                 Player defender,
                                                                 Set<UUID> usedAttackers) {
        List<Permanent> availableAttackers = attackingPlayer.getAvailableAttackers(defender.getId(), game)
                .stream()
                .filter(attacker -> !usedAttackers.contains(attacker.getId()))
                .collect(Collectors.toList());
        if (availableAttackers.isEmpty()) {
            return emptyList;
        }
        return canKillOpponent(game, availableAttackers, defender.getAvailableBlockers(game), defender);
    }

    private static List<Permanent> getMinimalLethalAttackers(Game game,
                                                             List<Permanent> blockableAttackers,
                                                             List<Permanent> unblockableAttackers,
                                                             int blockerCount,
                                                             int damageNeeded,
                                                             boolean poisonDamage,
                                                             boolean projectDeclaredAttackTriggers) {
        if (damageNeeded <= 0) {
            return emptyList;
        }

        List<Permanent> bestAttackers = emptyList;

        List<Permanent> selectedBlockable = getMinimalLethalBlockableAttackers(
                game,
                blockableAttackers,
                blockerCount,
                damageNeeded,
                poisonDamage,
                projectDeclaredAttackTriggers
        );
        bestAttackers = getBetterLethalCandidate(game, bestAttackers, selectedBlockable, poisonDamage, projectDeclaredAttackTriggers);

        List<Permanent> selectedUnblockable = new ArrayList<>();
        int selectedUnblockableDamage = 0;
        for (Permanent attacker : getRelevantLethalAttackers(game, unblockableAttackers, poisonDamage, projectDeclaredAttackTriggers)) {
            selectedUnblockable.add(attacker);
            selectedUnblockableDamage += getLethalDamageValue(attacker, game, poisonDamage, projectDeclaredAttackTriggers);
            int remainingDamage = damageNeeded - selectedUnblockableDamage;
            if (remainingDamage <= 0) {
                bestAttackers = getBetterLethalCandidate(
                        game,
                        bestAttackers,
                        new ArrayList<>(selectedUnblockable),
                        poisonDamage,
                        projectDeclaredAttackTriggers
                );
            } else {
                List<Permanent> selectedCombinedBlockable = getMinimalLethalBlockableAttackers(
                        game,
                        blockableAttackers,
                        blockerCount,
                        remainingDamage,
                        poisonDamage,
                        projectDeclaredAttackTriggers
                );
                if (!selectedCombinedBlockable.isEmpty()) {
                    List<Permanent> combinedAttackers = new ArrayList<>(selectedUnblockable);
                    combinedAttackers.addAll(selectedCombinedBlockable);
                    bestAttackers = getBetterLethalCandidate(game, bestAttackers, combinedAttackers, poisonDamage, projectDeclaredAttackTriggers);
                }
            }
        }

        return bestAttackers;
    }

    private static List<Permanent> getMinimalLethalBlockableAttackers(Game game,
                                                                      List<Permanent> blockableAttackers,
                                                                      int blockerCount,
                                                                      int damageNeeded,
                                                                      boolean poisonDamage,
                                                                      boolean projectDeclaredAttackTriggers) {
        List<Permanent> sortedBlockable = new ArrayList<>(blockableAttackers);
        sortedBlockable.sort(Comparator.comparingInt(p -> getLethalDamageValue(p, game, poisonDamage, projectDeclaredAttackTriggers)));
        Collections.reverse(sortedBlockable);

        for (int attackerCount = 1; attackerCount <= sortedBlockable.size(); attackerCount++) {
            List<Permanent> candidateBlockable = sortedBlockable.subList(0, attackerCount);
            if (sumGuaranteedUnblockedDamage(game, candidateBlockable, blockerCount, poisonDamage, projectDeclaredAttackTriggers) >= damageNeeded) {
                return new ArrayList<>(candidateBlockable);
            }
        }

        return emptyList;
    }

    private static List<Permanent> getRelevantLethalAttackers(Game game,
                                                              List<Permanent> attackers,
                                                              boolean poisonDamage,
                                                              boolean projectDeclaredAttackTriggers) {
        List<Permanent> relevantAttackers = new ArrayList<>();
        for (Permanent attacker : attackers) {
            if (getLethalDamageValue(attacker, game, poisonDamage, projectDeclaredAttackTriggers) > 0) {
                relevantAttackers.add(attacker);
            }
        }
        relevantAttackers.sort(Comparator.comparingInt(p -> getLethalDamageValue(p, game, poisonDamage, projectDeclaredAttackTriggers)));
        Collections.reverse(relevantAttackers);
        return relevantAttackers;
    }

    private static List<Permanent> getBetterLethalCandidate(Game game,
                                                            List<Permanent> bestAttackers,
                                                            List<Permanent> candidateAttackers,
                                                            boolean poisonDamage,
                                                            boolean projectDeclaredAttackTriggers) {
        if (candidateAttackers.isEmpty()) {
            return bestAttackers;
        }
        if (bestAttackers.isEmpty()) {
            return candidateAttackers;
        }
        if (candidateAttackers.size() != bestAttackers.size()) {
            return candidateAttackers.size() < bestAttackers.size() ? candidateAttackers : bestAttackers;
        }
        return sumLethalDamage(game, candidateAttackers, poisonDamage, projectDeclaredAttackTriggers) < sumLethalDamage(game, bestAttackers, poisonDamage, projectDeclaredAttackTriggers)
                ? candidateAttackers
                : bestAttackers;
    }

    private static int sumGuaranteedUnblockedDamage(Game game,
                                                    List<Permanent> sortedBlockableAttackers,
                                                    int blockerCount,
                                                    boolean poisonDamage,
                                                    boolean projectDeclaredAttackTriggers) {
        int damage = 0;
        for (int i = Math.min(blockerCount, sortedBlockableAttackers.size()); i < sortedBlockableAttackers.size(); i++) {
            damage += getLethalDamageValue(sortedBlockableAttackers.get(i), game, poisonDamage, projectDeclaredAttackTriggers);
        }
        return damage;
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

    public static void sortByCombatDamage(Game game, List<Permanent> permanents, final boolean ascending) {
        permanents.sort(Comparator.comparingInt(p -> getCombatDamageValue(p, game)));
        if (!ascending) {
            Collections.reverse(permanents);
        }
    }

    public static int getCombatDamageValue(Permanent permanent, Game game) {
        if (game.getCombat().useToughnessForDamage(permanent, game)) {
            return Math.max(0, permanent.getToughness().getValue());
        }
        return Math.max(0, permanent.getPower().getValue());
    }

    public static int getLikelyAttackingDamageValue(Permanent permanent, Game game) {
        return AttackTriggerProjection.likelyAttackingDamage(permanent, game);
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

    private static int sumLethalDamage(Game game, List<Permanent> attackersThatWontBeBlocked, boolean poisonDamage) {
        return sumLethalDamage(game, attackersThatWontBeBlocked, poisonDamage, false);
    }

    private static int sumLethalDamage(Game game, List<Permanent> attackersThatWontBeBlocked, boolean poisonDamage,
                                       boolean projectDeclaredAttackTriggers) {
        return poisonDamage
                ? sumPoisonDamage(game, attackersThatWontBeBlocked, projectDeclaredAttackTriggers)
                : sumDamage(game, attackersThatWontBeBlocked, projectDeclaredAttackTriggers);
    }

    private static int getLethalDamageValue(Permanent attacker, Game game, boolean poisonDamage) {
        return getLethalDamageValue(attacker, game, poisonDamage, false);
    }

    private static int getLethalDamageValue(Permanent attacker, Game game, boolean poisonDamage,
                                            boolean projectDeclaredAttackTriggers) {
        boolean hasInfect = attacker.getAbilities().contains(InfectAbility.getInstance());
        if (poisonDamage != hasInfect) {
            return 0;
        }
        int damage = getCombatDamageValue(attacker, game);
        if (!projectDeclaredAttackTriggers) {
            if (attacker.getAbilities(game).containsClass(mage.abilities.keyword.DoubleStrikeAbility.class)) {
                damage += getCombatDamageValue(attacker, game);
            }
            return damage;
        }
        AttackTriggerProjection.Projection projection = AttackTriggerProjection.projectDeclaredAttacker(attacker, game);
        if (projection.grantsDoubleStrike()) {
            damage += getCombatDamageValue(attacker, game);
        }
        damage += projection.getPowerBonus();
        if (projection.grantsDoubleStrike() && projection.getPowerBonus() > 0) {
            damage += projection.getPowerBonus();
        }
        return damage;
    }

    private static int sumDamage(Game game, List<Permanent> attackersThatWontBeBlocked) {
        return sumDamage(game, attackersThatWontBeBlocked, false);
    }

    private static int sumDamage(Game game, List<Permanent> attackersThatWontBeBlocked, boolean projectDeclaredAttackTriggers) {
        int damage = 0;
        for (Permanent attacker : attackersThatWontBeBlocked) {
            damage += getLethalDamageValue(attacker, game, false, projectDeclaredAttackTriggers);
        }
        return damage;
    }

    private static int sumPoisonDamage(Game game, List<Permanent> attackersThatWontBeBlocked) {
        return sumPoisonDamage(game, attackersThatWontBeBlocked, false);
    }

    private static int sumPoisonDamage(Game game, List<Permanent> attackersThatWontBeBlocked, boolean projectDeclaredAttackTriggers) {
        int damage = 0;
        for (Permanent attacker : attackersThatWontBeBlocked) {
            damage += getLethalDamageValue(attacker, game, true, projectDeclaredAttackTriggers);
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

        // TODO: implement full game simulations of all possible combinations (e.g. multiblockers support)

        CombatInfo combatInfo = new CombatInfo();
        for (Permanent attacker : attackers) {
            // simple combat simulation (1 vs 1)
            List<Permanent> allBlockers = getPossibleBlockers(game, attacker, blockers);
            List<SurviveInfo> blockerStats = getBlockersThatWillSurvive2(game, attackerId, defenderId, attacker, allBlockers);
            Map<Permanent, Integer> blockingDiffScore = new HashMap<>();
            Map<Permanent, Integer> nonBlockingDiffScore = new HashMap<>();
            Map<Permanent, SurviveInfo> surviveByBlocker = new HashMap<>();
            blockerStats.forEach(s -> {
                blockingDiffScore.put(s.getBlocker(), s.getDiffBlockingScore());
                nonBlockingDiffScore.put(s.getBlocker(), s.getDiffNonblockingScore());
                surviveByBlocker.put(s.getBlocker(), s);
            });

            // split blockers by usage priority
            List<Permanent> survivedAndKillBlocker = new ArrayList<>();
            List<Permanent> survivedBlockers = new ArrayList<>();
            List<Permanent> diedBlockers = new ArrayList<>();
            blockerStats.forEach(stats -> {
                if (stats.isAttackerDied() && !stats.isBlockerDied()) {
                    survivedAndKillBlocker.add(stats.getBlocker());
                } else if (!stats.isBlockerDied()) {
                    survivedBlockers.add(stats.getBlocker());
                } else {
                    diedBlockers.add(stats.getBlocker());
                }
            });

            int blockedCount = 0;

            // find good blocker
            Permanent blocker = getWorstCreature(survivedAndKillBlocker, survivedBlockers);
            if (blocker != null) {
                combatInfo.addPair(attacker, blocker);
                removeWorstCreature(blocker, blockers, survivedAndKillBlocker, survivedBlockers);
                blockedCount++;
            }

            // find good sacrifices (chump blocks also supported due bad game score on loose)
            // TODO: add chump blocking support here?
            // TODO: there are many triggers on damage, attack, etc - it can't be processed without real game simulations
            if (blocker == null) {
                blocker = getWorstCreature(diedBlockers);
                if (blocker != null) {
                    int diffBlockingScore = blockingDiffScore.getOrDefault(blocker, 0);
                    int diffNonBlockingScore = nonBlockingDiffScore.getOrDefault(blocker, 0);
                    if (diffBlockingScore >= 0 || diffBlockingScore > diffNonBlockingScore) {
                        // it's good - can sacrifice and get better game state, also protect from game loose
                        combatInfo.addPair(attacker, blocker);
                        removeWorstCreature(blocker, blockers, diedBlockers);
                        blockedCount++;
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
                    blocker = getWorstCreature(survivedBlockers, survivedAndKillBlocker, diedBlockers);
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
}

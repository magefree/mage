package mage.player.ai.util;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.InfectAbility;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.Combat;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.CombatDamageStep;
import mage.game.turn.EndOfCombatStep;
import mage.game.turn.FirstCombatDamageStep;
import mage.game.turn.Step;
import mage.players.Player;

import java.util.*;

/**
 * Base helper methods for combat.
 *
 * @author noxx
 */
public class CombatUtil {

    private static final List<Permanent> emptyList = new ArrayList<Permanent>();

    private static final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CombatUtil.class);

    private CombatUtil() {
    }

    public static List<Permanent> canKillOpponent(Game game, List<Permanent> attackersList, List<Permanent> blockersList,
                                                  Player defender) {
        List<Permanent> blockableAttackers = new ArrayList<Permanent>(blockersList);
        List<Permanent> unblockableAttackers = new ArrayList<Permanent>();
        for (Permanent attacker : attackersList) {
            if (!canBeBlocked(game, attacker, blockersList)) {
                unblockableAttackers.add(attacker);
                blockableAttackers.remove(attacker);
            }
        }

        sortByPower(blockableAttackers, true);

        // imagine that most powerful will be blocked as 1-vs-1
        List<Permanent> attackersThatWontBeBlocked = new ArrayList<Permanent>(blockableAttackers);
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

        if (sumPoisonDamage(attackersThatWontBeBlocked, defender) >= 10 - defender.getCounters().getCount(CounterType.POISON)) {
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
        Collections.sort(permanents, new Comparator<Permanent>() {
            @Override
            public int compare(Permanent o1, Permanent o2) {
                if (ascending) {
                    return o2.getPower().getValue() - o1.getPower().getValue();
                } else {
                    return o1.getPower().getValue() - o2.getPower().getValue();
                }
            }
        });
    }

    public static Permanent getWorstCreature(List<Permanent> creatures) {
        if (creatures.isEmpty()) {
            return null;
        }
        Collections.sort(creatures, new Comparator<Permanent>() {
            @Override
            public int compare(Permanent o1, Permanent o2) {
                return o2.getPower().getValue() - o1.getPower().getValue();
            }
        });
        return creatures.get(0);
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
        List<Permanent> canBlock = new ArrayList<Permanent>();
        for (Permanent blocker : blockersList) {
            if (blocker.canBlock(attacker.getId(), game)) {
                canBlock.add(blocker);
            }
        }
        return canBlock;
    }

    public static CombatInfo blockWithGoodTrade(Game game, List<Permanent> attackers, List<Permanent> blockers) {

        UUID attackerId = game.getCombat().getAttackerId();
        UUID defenderId = game.getCombat().getDefenders().iterator().next();
        if (attackerId == null || defenderId == null) {
            log.warn("Couldn't find attacker or defender: " + attackerId + " " + defenderId);
            return new CombatInfo();
        }

        CombatInfo combatInfo = new CombatInfo();
        for (Permanent attacker : attackers) {
            //TODO: handle attackers with "can't be blocked except"
            List<Permanent> possibleBlockers = getPossibleBlockers(game, attacker, blockers);
            List<Permanent> survivedBlockers = getBlockersThatWillSurvive(game, attackerId, defenderId, attacker, possibleBlockers);
            if (!survivedBlockers.isEmpty()) {
                Permanent blocker = getWorstCreature(survivedBlockers);
                combatInfo.addPair(attacker, blocker);
                blockers.remove(blocker);
            }
            if (blockers.isEmpty()) {
                break;
            }
        }

        return combatInfo;
    }

    private static List<Permanent> getBlockersThatWillSurvive(Game game, UUID attackerId, UUID defenderId, Permanent attacker, List<Permanent> possibleBlockers) {
        List<Permanent> blockers = new ArrayList<Permanent>();
        for (Permanent blocker : possibleBlockers) {
            SurviveInfo info = willItSurvive(game, attackerId, defenderId, attacker, blocker);
            //if (info.isAttackerDied() && !info.isBlockerDied()) {
            if (info != null) {
                if (info.isAttackerDied()) {
                    blockers.add(blocker);
                } else if (!info.isBlockerDied()) {
                    blockers.add(blocker);
                }
            }
        }
        return blockers;
    }

    public static SurviveInfo willItSurvive(Game game, UUID attackingPlayerId, UUID defendingPlayerId, Permanent attacker, Permanent blocker) {
        Game sim = game.copy();

        Combat combat = sim.getCombat();
        combat.setAttacker(attackingPlayerId);
        combat.setDefenders(sim);

        if (blocker == null || attacker == null || sim.getPlayer(defendingPlayerId) == null) {
            return null;
        }

        sim.getPlayer(defendingPlayerId).declareBlocker(blocker.getId(), attacker.getId(), sim);
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defendingPlayerId, defendingPlayerId));

        sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));

        simulateStep(sim, new FirstCombatDamageStep());
        simulateStep(sim, new CombatDamageStep());
        simulateStep(sim, new EndOfCombatStep());
        // The following commented out call produces random freezes.
        //sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }

        return new SurviveInfo(!sim.getBattlefield().containsPermanent(attacker.getId()), !sim.getBattlefield().containsPermanent(blocker.getId()));
    }

    public static SurviveInfo getCombatInfo(Game game, UUID attackingPlayerId, UUID defendingPlayerId, Permanent attacker) {
        Game sim = game.copy();

        Combat combat = sim.getCombat();
        combat.setAttacker(attackingPlayerId);
        combat.setDefenders(sim);

        UUID defenderId = sim.getCombat().getDefenders().iterator().next();
        boolean triggered = false;

        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defendingPlayerId, defendingPlayerId));

        sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            triggered = true;
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));

        simulateStep(sim, new FirstCombatDamageStep());
        simulateStep(sim, new CombatDamageStep());
        simulateStep(sim, new EndOfCombatStep());
        // The following commented out call produces random freezes.
        //sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            triggered = true;
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }

        return new SurviveInfo(!sim.getBattlefield().containsPermanent(attacker.getId()), false, sim.getPlayer(defenderId), triggered);
    }

    protected static void simulateStep(Game game, Step step) {
        game.getPhase().setStep(step);
        if (!step.skipStep(game, game.getActivePlayerId())) {
            step.beginStep(game, game.getActivePlayerId());
            // The following commented out call produces random freezes.
            //game.checkStateAndTriggered();
            while (!game.getStack().isEmpty()) {
                game.getStack().resolve(game);
                game.applyEffects();
            }
            step.endStep(game, game.getActivePlayerId());
        }
    }

    public static boolean canBlock(Game game, Permanent blocker) {
        boolean canBlock = true;
        if (!blocker.isTapped()) {
            try {
                canBlock = blocker.canBlock(null, game);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return canBlock;
    }
    
    public static CombatInfo blockWithGoodTrade2(Game game, List<Permanent> attackers, List<Permanent> blockers) {

        UUID attackerId = game.getCombat().getAttackerId();
        UUID defenderId = game.getCombat().getDefenders().iterator().next();
        if (attackerId == null || defenderId == null) {
            log.warn("Couldn't find attacker or defender: " + attackerId + " " + defenderId);
            return new CombatInfo();
        }

        CombatInfo combatInfo = new CombatInfo();
        for (Permanent attacker : attackers) {
            //TODO: handle attackers with "can't be blocked except"
            List<Permanent> possibleBlockers = getPossibleBlockers(game, attacker, blockers);
            List<Permanent> survivedBlockers = getBlockersThatWillSurvive2(game, attackerId, defenderId, attacker, possibleBlockers);
            if (!survivedBlockers.isEmpty()) {
                Permanent blocker = getWorstCreature(survivedBlockers);
                combatInfo.addPair(attacker, blocker);
                blockers.remove(blocker);
            }
            if (blockers.isEmpty()) {
                break;
            }
        }

        return combatInfo;
    }
    
    private static List<Permanent> getBlockersThatWillSurvive2(Game game, UUID attackerId, UUID defenderId, Permanent attacker, List<Permanent> possibleBlockers) {
        List<Permanent> blockers = new ArrayList<Permanent>();
        for (Permanent blocker : possibleBlockers) {
            SurviveInfo info = willItSurvive2(game, attackerId, defenderId, attacker, blocker);
            //if (info.isAttackerDied() && !info.isBlockerDied()) {
            if (info != null) {
                if (info.isAttackerDied()) {
                    blockers.add(blocker);
                } else if (!info.isBlockerDied()) {
                    blockers.add(blocker);
                }
            }
        }
        return blockers;
    }
    
    public static SurviveInfo willItSurvive2(Game game, UUID attackingPlayerId, UUID defendingPlayerId, Permanent attacker, Permanent blocker) {
        
        Game sim = game.copy();

        Combat combat = sim.getCombat();
        combat.setAttacker(attackingPlayerId);
        combat.setDefenders(sim);

        if (blocker == null || attacker == null || sim.getPlayer(defendingPlayerId) == null) {
            return null;
        }
        
        if (attacker.getPower().getValue() >= blocker.getToughness().getValue()) {
            sim.getBattlefield().removePermanent(blocker.getId());
        }
        if (attacker.getToughness().getValue() <= blocker.getPower().getValue()) {
            sim.getBattlefield().removePermanent(attacker.getId());
        }
        
        /*
        sim.getPlayer(defendingPlayerId).declareBlocker(blocker.getId(), attacker.getId(), sim);
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARED_BLOCKERS, defendingPlayerId, defendingPlayerId));

        sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }
        sim.fireEvent(GameEvent.getEvent(GameEvent.EventType.DECLARE_BLOCKERS_STEP_POST, sim.getActivePlayerId(), sim.getActivePlayerId()));

        simulateStep(sim, new FirstCombatDamageStep());
        simulateStep(sim, new CombatDamageStep());
        simulateStep(sim, new EndOfCombatStep());
        // The following commented out call produces random freezes.
        //sim.checkStateAndTriggered();
        while (!sim.getStack().isEmpty()) {
            sim.getStack().resolve(sim);
            sim.applyEffects();
        }
        */

        return new SurviveInfo(!sim.getBattlefield().containsPermanent(attacker.getId()), !sim.getBattlefield().containsPermanent(blocker.getId()));
    }

}

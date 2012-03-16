package mage.player.ai.util;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.InfectAbility;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Base helper methods for combat.
 *
 * @author noxx
 */
public class CombatUtil {

    private static final List<Permanent> emptyList = new ArrayList<Permanent>();

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

        sortByPower(blockableAttackers);

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

    private static void sortByPower(List<Permanent> permanents) {
        Collections.sort(permanents, new Comparator<Permanent>() {
            @Override
            public int compare(Permanent o1, Permanent o2) {
                return o2.getPower().getValue() - o1.getPower().getValue();
            }
        });
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
}

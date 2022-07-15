package mage.player.ai.simulators;

import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CombatSimulator implements Serializable {

    public List<CombatGroupSimulator> groups = new ArrayList<>();
    public List<UUID> defenders = new ArrayList<>();
    public Map<UUID, Integer> playersLife = new HashMap<>();
    public Map<UUID, Integer> planeswalkerLoyalty = new HashMap<>(); // TODO: Doesn't seem to be used, needed?
    public UUID attackerId;
    public int rating = 0;

    public static CombatSimulator load(Game game) {
        CombatSimulator simCombat = new CombatSimulator();
        for (CombatGroup group: game.getCombat().getGroups()) {
            simCombat.groups.add(new CombatGroupSimulator(group.getDefenderId(), group.getAttackers(), group.getBlockers(), game));
        }
        for (UUID defenderId: game.getCombat().getDefenders()) {
            simCombat.defenders.add(defenderId);
            Player player = game.getPlayer(defenderId);
            if (player != null) {
                simCombat.playersLife.put(defenderId, player.getLife());
            }
            else {
                Permanent permanent = game.getPermanent(defenderId);
                simCombat.planeswalkerLoyalty.put(defenderId, permanent.getCounters(game).getCount(CounterType.LOYALTY));
            }
        }
        return simCombat;
    }

    public CombatSimulator() {

    }

    public void clear() {
        groups.clear();
        defenders.clear();
        attackerId = null;
    }

    public void simulate(Game game) {
        for (CombatGroupSimulator group: groups) {
            group.simulateCombat(game);
        }
    }

    public int evaluate() {
        Map<UUID, Integer> damage = new HashMap<>();
        int result = 0;
        for (CombatGroupSimulator group: groups) {
            if (!damage.containsKey(group.defenderId)) {
                damage.put(group.defenderId, group.unblockedDamage);
            }
            else {
                damage.put(group.defenderId, damage.get(group.defenderId) + group.unblockedDamage);
            }
        }
        //check for lethal damage to player
        for (Entry<UUID, Integer> entry: playersLife.entrySet()) {
            if (damage.containsKey(entry.getKey()) && entry.getValue() <= damage.get(entry.getKey())) {
                //TODO:  check for protection
                //NOTE:  not applicable for mulitplayer games
                return Integer.MAX_VALUE;
            }
        }

        for (CombatGroupSimulator group: groups) {
            result += group.evaluateCombat();
        }

        rating = result;
        return result;
    }

    @Override
    public int hashCode() {
        return 0; // TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        CombatSimulator that = (CombatSimulator) o;

        // Order below is in increasing order of complexity (and decreasing order of singal value)
        if (this.rating != that.rating) {
            return false;
        }
        if (!Objects.equals(this.attackerId, that.attackerId)) {
            return false;
        }
        if (!Objects.equals(this.playersLife, that.playersLife)) {
            return false;
        }
        if (!Objects.equals(this.planeswalkerLoyalty, that.planeswalkerLoyalty)) {
            return false;
        }

        if (!Objects.deepEquals(this.defenders, that.defenders)) { // TODO: This is not correct. Can't use UUID to compare. Remove other .deepEquals
            return false;
        }

        return Objects.deepEquals(this.groups, that.groups);
    }
}

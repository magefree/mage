
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.PlayerAttackedStepWatcher;

/**
 * A condition which checks whether any players being attacked are poisoned
 * (have one or more poison counters on them)
 * 
 * @author alexander-novo
 */
public enum AttackedPlayersPoisonedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat().getDefenders().stream().map(defender -> game.getPlayer(defender))
                .anyMatch(player -> player != null && player.getCounters().containsKey(CounterType.POISON));
    }

    @Override
    public String toString() {
        return "one or more players being attacked are poisoned";
    }
}
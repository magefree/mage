
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 * Describes condition when an opponent has been dealt any amount of non-combat
 * damage
 *
 * @author Styxo
 */
public enum HateCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        LifeLossOtherFromCombatWatcher watcher = game.getState().getWatcher(LifeLossOtherFromCombatWatcher.class);
        return watcher != null && watcher.opponentLostLifeOtherFromCombat(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "if an opponent lost life from source other than combat damage this turn";
    }
}

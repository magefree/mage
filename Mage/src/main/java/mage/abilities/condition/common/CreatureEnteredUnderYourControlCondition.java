package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CreatureEnteredControllerWatcher;

/**
 * @author TheElk801
 */
public enum CreatureEnteredUnderYourControlCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CreatureEnteredControllerWatcher.enteredCreatureForPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a creature entered the battlefield under your control this turn";
    }
}

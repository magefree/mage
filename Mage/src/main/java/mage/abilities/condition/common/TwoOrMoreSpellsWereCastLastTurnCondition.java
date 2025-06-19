package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author nantuko
 */
public enum TwoOrMoreSpellsWereCastLastTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsCastOnPrevTurn()
                .values()
                .stream()
                .anyMatch(x -> x >= 2);
    }

    @Override
    public String toString() {
        return "a player cast two or more spells last turn";
    }
}

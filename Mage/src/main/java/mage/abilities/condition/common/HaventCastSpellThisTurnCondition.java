package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author Susucr
 */
public enum HaventCastSpellThisTurnCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .isEmpty();
    }

    @Override
    public String toString() {
        return "if you haven't cast a spell this turn";
    }
}

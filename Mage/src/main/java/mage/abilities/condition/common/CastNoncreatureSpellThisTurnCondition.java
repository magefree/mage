package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

/**
 * @author TheElk801
 */
public enum CastNoncreatureSpellThisTurnCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .anyMatch(spell -> !spell.isCreature(game));
    }

    @Override
    public String toString() {
        return "you've cast a noncreature spell this turn";
    }
}

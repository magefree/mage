package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;

/**
 * @author Susucr
 */
public enum HaventCastSpellFromHandThisTurnCondition implements Condition {
    instance;

    public static final Hint hint = new ConditionHint(instance, "No spell cast from hand this turn", null, "Have cast spell from hand this turn", null, true);

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState()
                .getWatcher(SpellsCastWatcher.class)
                .getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .noneMatch(spell -> Zone.HAND.equals(spell.getFromZone()));
    }

    @Override
    public String toString() {
        return "if you haven't cast a spell from hand this turn";
    }
}

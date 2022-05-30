package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.CreaturePutIntoGraveyardWatcher;

/**
 * @author TheElk801
 */
public enum CreaturePutInYourGraveyardCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "A creature card was put into your graveyard this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CreaturePutIntoGraveyardWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a creature card was put into your graveyard from anywhere this turn";
    }
}

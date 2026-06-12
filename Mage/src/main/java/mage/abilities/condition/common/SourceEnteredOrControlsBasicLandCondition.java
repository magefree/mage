package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 *
 * @author muz
 */
public enum SourceEnteredOrControlsBasicLandCondition implements Condition {
    instance;

    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SourceEnteredThisTurnCondition.DID.apply(game, source)
            || YouControlABasicLandCondition.instance.apply(game, source);
    }

    @Override
    public String toString() {
        return "{this} entered the battlefield this turn or you control a basic land";
    }
}

package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.game.Game;

import java.awt.*;

/**
 * ConditionHint that is only shown on permanents.
 *
 * @author Susucr
 */
public class ConditionPermanentHint extends ConditionHint {

    public ConditionPermanentHint(Condition condition) {
        super(condition);
    }

    public ConditionPermanentHint(Condition condition, String textWithIcons) {
        super(condition, textWithIcons);
    }

    public ConditionPermanentHint(Condition condition, String trueText, Color trueColor, String falseText, Color falseColor, Boolean useIcons) {
        super(condition, trueText, trueColor, falseText, falseColor, useIcons);
    }

    private ConditionPermanentHint(final ConditionPermanentHint hint) {
        super(hint);
    }

    @Override
    public String getText(Game game, Ability ability) {
        if (game.getPermanent(ability.getSourceId()) == null) {
            return "";
        }

        return super.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return new ConditionPermanentHint(this);
    }
}

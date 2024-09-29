package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.util.CardUtil;

import java.awt.*;

/**
 * Displays an hint only when the condition is true.
 *
 * @author Susucr
 */
public class ConditionTrueHint implements Hint {

    private Condition condition;
    private String trueText;
    private Color trueColor;
    private Boolean useIcons;

    public ConditionTrueHint(Condition condition) {
        this(condition, condition.toString());
    }

    public ConditionTrueHint(Condition condition, String textWithIcons) {
        this(condition, textWithIcons, null, true);
    }

    public ConditionTrueHint(Condition condition, String trueText, Color trueColor, Boolean useIcons) {
        this.condition = condition;
        this.trueText = CardUtil.getTextWithFirstCharUpperCase(trueText);
        this.trueColor = trueColor;
        this.useIcons = useIcons;
    }

    protected ConditionTrueHint(final ConditionTrueHint hint) {
        this.condition = hint.condition;
        this.trueText = hint.trueText;
        this.trueColor = hint.trueColor;
        this.useIcons = hint.useIcons;
    }

    @Override
    public String getText(Game game, Ability ability) {
        String icon;
        if (condition.apply(game, ability)) {
            icon = this.useIcons ? HintUtils.HINT_ICON_GOOD : null;
            return HintUtils.prepareText(this.trueText, this.trueColor, icon);
        }
        return "";
    }

    @Override
    public Hint copy() {
        return new ConditionTrueHint(this);
    }
}

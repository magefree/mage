package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.util.CardUtil;

import java.awt.*;

/**
 * @author JayDi85
 */
public class ConditionHint implements Hint {

    private Condition condition;
    private String trueText;
    private Color trueColor;
    private String falseText;
    private Color falseColor;
    private Boolean useIcons;

    public ConditionHint(Condition condition) {
        this(condition, condition.toString());
    }

    public ConditionHint(Condition condition, String textWithIcons) {
        this(condition, textWithIcons, null, textWithIcons, null, true);
    }

    public ConditionHint(Condition condition, String trueText, Color trueColor, String falseText, Color falseColor, Boolean useIcons) {
        this.condition = condition;
        this.trueText = CardUtil.getTextWithFirstCharUpperCase(trueText);
        this.trueColor = trueColor;
        this.falseText = CardUtil.getTextWithFirstCharUpperCase(falseText);
        this.falseColor = falseColor;
        this.useIcons = useIcons;
    }

    protected ConditionHint(final ConditionHint hint) {
        this.condition = hint.condition;
        this.trueText = hint.trueText;
        this.trueColor = hint.trueColor;
        this.falseText = hint.falseText;
        this.falseColor = hint.falseColor;
        this.useIcons = hint.useIcons;
    }

    @Override
    public String getText(Game game, Ability ability) {
        String icon;
        if (condition.apply(game, ability)) {
            icon = this.useIcons ? HintUtils.HINT_ICON_GOOD : null;
            return HintUtils.prepareText(this.trueText, this.trueColor, icon);
        } else {
            icon = this.useIcons ? HintUtils.HINT_ICON_BAD : null;
            return HintUtils.prepareText(this.falseText, this.falseColor, icon);
        }
    }

    @Override
    public Hint copy() {
        return new ConditionHint(this);
    }
}

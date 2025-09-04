package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.WebSlingingAbility;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum WebSlingingCondition implements Condition {
    THEY("they were"),
    THIS("{this}");
    private final String message;

    WebSlingingCondition(String message) {
        this.message = message;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.checkSourceCostsTagExists(game, source, WebSlingingAbility.WEB_SLINGING_ACTIVATION_VALUE_KEY);
    }

    @Override
    public String toString() {
        return message + " cast using web-slinging";
    }
}

package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;

/**
 * @author TheElk801
 */
public enum DayNightHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        if (game.checkDayNight(true)) {
            return "It's currently day";
        } else if (game.checkDayNight(false)) {
            return "It's currently night";
        } else {
            return "It's neither day nor night";
        }
    }

    @Override
    public Hint copy() {
        return this;
    }
}

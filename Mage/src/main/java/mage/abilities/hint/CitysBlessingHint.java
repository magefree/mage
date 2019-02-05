package mage.abilities.hint;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.game.Game;

/**
 * @author JayDi85
 */
public enum CitysBlessingHint implements Hint {

    instance;
    private static final ConditionHint hint = new ConditionHint(CitysBlessingCondition.instance, "Have city's blessing");

    @Override
    public String getText(Game game, Ability ability) {
        return hint.getText(game, ability);
    }

    @Override
    public Hint copy() {
        return instance;
    }
}

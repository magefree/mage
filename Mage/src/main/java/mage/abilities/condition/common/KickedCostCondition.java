package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.KickerAbility;
import mage.game.Game;

/**
 * Describes condition when specific KickerCosts were paid.
 *
 * @author LevelX2
 */
public class KickedCostCondition implements Condition {

    protected String kickerCostText;

    public  KickedCostCondition(String kickerCostText) {
        this.kickerCostText = kickerCostText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return KickerAbility.getKickedCounterStrict(game, source, kickerCostText) > 0;
    }
}

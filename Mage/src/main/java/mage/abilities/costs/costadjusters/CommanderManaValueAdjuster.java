package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.dynamicvalue.common.GreatestCommanderManaValue;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum CommanderManaValueAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, GreatestCommanderManaValue.instance.calculate(game, ability, null));
    }
}

package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum DomainAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        CardUtil.reduceCost(ability, DomainValue.REGULAR.calculate(game, ability, null));
    }
}

package mage.abilities.costs.costadjusters;

import mage.abilities.Ability;
import mage.abilities.costs.CostAdjuster;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public enum LegendaryCreatureCostAdjuster implements CostAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int count = game.getBattlefield().count(
                filter, ability.getSourceId(), ability.getControllerId(), game
        );
        if (count > 0) {
            CardUtil.reduceCost(ability, count);
        }
    }
}
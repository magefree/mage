package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class SacrificedPermanentCondition implements Condition {

    private final FilterPermanent filter;
    private final String staticText;

    public SacrificedPermanentCondition(FilterPermanent filter) {
        this.filter = filter;
        this.staticText = "the sacrificed permanent was " + filter.getMessage();
    }

    public SacrificedPermanentCondition(FilterPermanent filter, String staticText) {
        this.filter = filter;
        this.staticText = staticText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                for (Permanent permanent : ((SacrificeTargetCost) cost).getPermanents()) {
                    if (filter.match(permanent, source.getControllerId(), source, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return staticText;
    }
}

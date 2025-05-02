package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Collection;

public class SacrificedPermanentCondition implements Condition {

    private final FilterPermanent filter;
    private boolean useThisWay = false;

    public SacrificedPermanentCondition(FilterPermanent filter) {
        this.filter = filter;
    }

    public SacrificedPermanentCondition(FilterPermanent filter, boolean useThisWay) {
        this.filter = filter;
        this.useThisWay = useThisWay;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil.castStream(source.getCosts()
                .stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .anyMatch(permanent -> filter.match(permanent, source.getControllerId(), source, game));
    }

    @Override
    public String toString() {
        if (useThisWay) {
            return "if " + filter.getMessage() + " was sacrificed this way";
        }
        return "if the sacrificed " + filter.getMessage();
    }
}

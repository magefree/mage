package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public enum XTargetsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = CardUtil.getSourceCostsTag(game, ability, "X", 0);
        FilterPermanent permanentFilter = ((TargetPermanent) ability.getTargets().get(0)).getFilter();
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(xValue, permanentFilter));
    }
}

package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public enum XCMCPermanentAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        TargetPermanent oldTargetPermanent = (TargetPermanent) ability.getTargets().get(0);
        int minTargets = oldTargetPermanent.getMinNumberOfTargets();
        int maxTargets = oldTargetPermanent.getMaxNumberOfTargets();
        FilterPermanent permanentFilter = oldTargetPermanent.getFilter().copy();
        permanentFilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.getTargets().add(new TargetPermanent(minTargets, maxTargets, permanentFilter, false));
    }
}

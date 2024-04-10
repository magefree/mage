package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.Target;

/**
 *
 * @author TheElk801, notgreat
 */
public class XMVAdjuster implements TargetAdjuster {
    private Target blueprintTarget = null;

    public XMVAdjuster() {
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (blueprintTarget == null) {
            blueprintTarget = ability.getTargets().get(0).copy();
        }
        int xValue = ability.getManaCostsToPay().getX();
        Target newTarget = blueprintTarget.copy();
        newTarget.getFilter().add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.addTarget(newTarget);
    }
}

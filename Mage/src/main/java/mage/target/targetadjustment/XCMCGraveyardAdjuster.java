package mage.target.targetadjustment;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public enum XCMCGraveyardAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filterCard = ((TargetCard) ability.getTargets().get(0)).getFilter().copy();
        filterCard.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        filterCard.setMessage(filterCard.getMessage().replace('X', (char) xValue));
        ability.getTargets().clear();
        ability.getTargets().add(new TargetCardInGraveyard(filterCard));
    }
}

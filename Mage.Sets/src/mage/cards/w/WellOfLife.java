
package mage.cards.w;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WellOfLife extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("you control no untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public WellOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your end step, if you control no untapped lands, you gain 2 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new GainLifeEffect(2)).withInterveningIf(condition));
    }

    private WellOfLife(final WellOfLife card) {
        super(card);
    }

    @Override
    public WellOfLife copy() {
        return new WellOfLife(this);
    }
}

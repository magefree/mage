package mage.cards.w;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class WellOfDiscovery extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("you control no untapped lands");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public WellOfDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // At the beginning of your end step, if you control no untapped lands, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1)).withInterveningIf(condition));
    }

    private WellOfDiscovery(final WellOfDiscovery card) {
        super(card);
    }

    @Override
    public WellOfDiscovery copy() {
        return new WellOfDiscovery(this);
    }
}

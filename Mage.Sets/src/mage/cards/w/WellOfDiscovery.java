
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author LoneFox
 */
public final class WellOfDiscovery extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public WellOfDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // At the beginning of your end step, if you control no untapped lands, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
            new DrawCardSourceControllerEffect(1), TargetController.YOU, false),
            new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "At the beginning of your end step, if you control no untapped lands, draw a card."));
    }

    private WellOfDiscovery(final WellOfDiscovery card) {
        super(card);
    }

    @Override
    public WellOfDiscovery copy() {
        return new WellOfDiscovery(this);
    }
}


package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class WellOfLife extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public WellOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // At the beginning of your end step, if you control no untapped lands, you gain 2 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
            new GainLifeEffect(2), TargetController.YOU, false), new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
            "At the beginning of your end step, if you control no untapped lands, you gain 2 life."));
    }

    private WellOfLife(final WellOfLife card) {
        super(card);
    }

    @Override
    public WellOfLife copy() {
        return new WellOfLife(this);
    }
}

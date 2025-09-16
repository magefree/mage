package mage.cards.r;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RentIsDue extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped creatures and/or Treasures you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.TREASURE.getPredicate()
        ));
    }

    public RentIsDue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // At the beginning of your end step, you may tap two untapped creatures and/or Treasures you control. If you do, draw a card. Otherwise, sacrifice this enchantment.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeSourceEffect(), new TapTargetCost(2, filter)
        ).setOtherwiseText("Otherwise")));
    }

    private RentIsDue(final RentIsDue card) {
        super(card);
    }

    @Override
    public RentIsDue copy() {
        return new RentIsDue(this);
    }
}

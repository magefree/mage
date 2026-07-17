package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class VillainousSyndication extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Villain you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.VILLAIN.getPredicate());
    }

    public VillainousSyndication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.PLAN);

        // Tap an untapped Villain you control: Mill a card. Put a plan counter on this enchantment. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new MillCardsControllerEffect(1),
            new TapTargetCost(new TargetControlledPermanent(1, 1, filter, false))
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()));
        this.addAbility(ability);

        // When the fourth plan counter is put on this enchantment, sacrifice it. When you do, return target creature card from your graveyard to the battlefield.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ReturnFromGraveyardToBattlefieldTargetEffect(), false,
            "return target creature card from your graveyard to the battlefield"
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new PlanCounterThresholdTriggeredAbility(4, reflexive));
    }

    private VillainousSyndication(final VillainousSyndication card) {
        super(card);
    }

    @Override
    public VillainousSyndication copy() {
        return new VillainousSyndication(this);
    }
}

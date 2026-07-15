package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedOneOrMoreTriggeredAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class RewriteHistory extends CardImpl {

    private static final FilterInstantOrSorceryCard filter
        = new FilterInstantOrSorceryCard("instant and/or sorcery cards from your graveyard");

    public RewriteHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.PLAN);

        // Whenever one or more creatures you control become tapped, draw a card, then discard a card and put a plan counter on this enchantment.
        Ability ability = new BecomesTappedOneOrMoreTriggeredAbility(
            Zone.BATTLEFIELD, new DrawDiscardControllerEffect(), false, StaticFilters.FILTER_CONTROLLED_CREATURES
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the fourth plan counter is put on this enchantment, sacrifice it. When you do, return up to two target instant and/or sorcery cards from your graveyard to your hand.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new ReturnFromGraveyardToHandTargetEffect(),
            false
        );
        reflexive.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.addAbility(new PlanCounterThresholdTriggeredAbility(4, reflexive));
    }

    private RewriteHistory(final RewriteHistory card) {
        super(card);
    }

    @Override
    public RewriteHistory copy() {
        return new RewriteHistory(this);
    }
}

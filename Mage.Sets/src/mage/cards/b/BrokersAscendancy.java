package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrokersAscendancy extends CardImpl {

    public BrokersAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{U}{W}");

        // At the beginning of your end step, put a +1/+1 counter on each creature you control and a loyalty counter on each planeswalker you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), TargetController.YOU, false);
        ability.addEffect(new AddCountersAllEffect(
                CounterType.LOYALTY.createInstance(),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("and a loyalty counter on each planeswalker you control"));
        this.addAbility(ability);
    }

    private BrokersAscendancy(final BrokersAscendancy card) {
        super(card);
    }

    @Override
    public BrokersAscendancy copy() {
        return new BrokersAscendancy(this);
    }
}

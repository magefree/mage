package mage.cards.p;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PoliticalTriumph extends CardImpl {

    public PoliticalTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.PLAN);

        // Whenever a creature you control enters, scry 1 and put a plan counter on this enchantment.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new ScryEffect(1), StaticFilters.FILTER_PERMANENT_A_CREATURE);
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the fourth plan counter is put on this enchantment, sacrifice it, draw a card, and put a +1/+1 counter on each creature you control.
        Ability thresholdAbility = new PlanCounterThresholdTriggeredAbility(4);
        thresholdAbility.addEffect(new DrawCardSourceControllerEffect(1).concatBy(","));
        thresholdAbility.addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE).concatBy(", and"));
        this.addAbility(thresholdAbility);
    }

    private PoliticalTriumph(final PoliticalTriumph card) {
        super(card);
    }

    @Override
    public PoliticalTriumph copy() {
        return new PoliticalTriumph(this);
    }
}

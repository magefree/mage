package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BellowingAegisaur extends CardImpl {

    public BellowingAegisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Enrage - Whenever Bellowing Aegisaur is dealt damage, put a +1/+1 counter on each other creature you control.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE), false, true);
        this.addAbility(ability);
    }

    private BellowingAegisaur(final BellowingAegisaur card) {
        super(card);
    }

    @Override
    public BellowingAegisaur copy() {
        return new BellowingAegisaur(this);
    }
}

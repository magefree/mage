package mage.cards.r;

import mage.abilities.common.DealtDamageAnyTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class RiteOfPassage extends CardImpl {

    public RiteOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever a creature you control is dealt damage, put a +1/+1 counter on it.
        this.addAbility(new DealtDamageAnyTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, SetTargetPointer.PERMANENT, false));

    }

    private RiteOfPassage(final RiteOfPassage card) {
        super(card);
    }

    @Override
    public RiteOfPassage copy() {
        return new RiteOfPassage(this);
    }
}

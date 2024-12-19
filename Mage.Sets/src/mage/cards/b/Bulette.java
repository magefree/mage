package mage.cards.b;

import mage.MageInt;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class Bulette extends CardImpl {

    public Bulette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if a creature died this turn, put a +1/+1 counter on Bulette.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf(MorbidCondition.instance).addHint(MorbidHint.instance));
    }

    private Bulette(final Bulette card) {
        super(card);
    }

    @Override
    public Bulette copy() {
        return new Bulette(this);
    }
}

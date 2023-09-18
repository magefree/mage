package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlourishingFox extends CardImpl {

    public FlourishingFox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.FOX);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cycle another card, put a +1/+1 counter on Flourishing Fox.
        this.addAbility(new CycleControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true
        ));

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private FlourishingFox(final FlourishingFox card) {
        super(card);
    }

    @Override
    public FlourishingFox copy() {
        return new FlourishingFox(this);
    }
}

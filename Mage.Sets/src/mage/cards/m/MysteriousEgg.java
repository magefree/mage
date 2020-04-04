package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysteriousEgg extends CardImpl {

    public MysteriousEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever this creature mutates, put a +1/+1 counter on it.
        this.addAbility(new MutatesSourceTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it")
        ));
    }

    private MysteriousEgg(final MysteriousEgg card) {
        super(card);
    }

    @Override
    public MysteriousEgg copy() {
        return new MysteriousEgg(this);
    }
}

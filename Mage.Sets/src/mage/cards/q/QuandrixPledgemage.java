package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.MagecraftAbility;
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
public final class QuandrixPledgemage extends CardImpl {

    public QuandrixPledgemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}{G/U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, put a +1/+1 counter on Quandrix Pledgemage.
        this.addAbility(new MagecraftAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private QuandrixPledgemage(final QuandrixPledgemage card) {
        super(card);
    }

    @Override
    public QuandrixPledgemage copy() {
        return new QuandrixPledgemage(this);
    }
}

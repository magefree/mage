package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
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
public final class MonkOfTheOpenHand extends CardImpl {

    public MonkOfTheOpenHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flurry of Blows — Whenever you cast your second spell each turn, put a +1/+1 counter on Monk of the Open Hand.
        this.addAbility(new CastSecondSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ).withFlavorWord("Flurry of Blows"));
    }

    private MonkOfTheOpenHand(final MonkOfTheOpenHand card) {
        super(card);
    }

    @Override
    public MonkOfTheOpenHand copy() {
        return new MonkOfTheOpenHand(this);
    }
}

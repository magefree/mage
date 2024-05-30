package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FaithfulWatchdog extends CardImpl {

    public FaithfulWatchdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Faithful Watchdog enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                "{this} enters the battlefield with three +1/+1 counters on it"
        ));
    }

    private FaithfulWatchdog(final FaithfulWatchdog card) {
        super(card);
    }

    @Override
    public FaithfulWatchdog copy() {
        return new FaithfulWatchdog(this);
    }
}

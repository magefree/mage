package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TirelessTracker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.CLUE, "a Clue");

    public TirelessTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a land enters the battlefield under your control, investigate. <i>(Create a colorless Clue artifact token with "{2}, Sacrifice this artifact: Draw a card.")</i>
        this.addAbility(new LandfallAbility(new InvestigateEffect()));

        // Whenever you sacrifice a Clue, put a +1/+1 counter on Tireless Tracker.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private TirelessTracker(final TirelessTracker card) {
        super(card);
    }

    @Override
    public TirelessTracker copy() {
        return new TirelessTracker(this);
    }
}

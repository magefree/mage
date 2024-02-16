package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author markedagain
 */
public final class KnuckleboneWitch extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    public KnuckleboneWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Goblin you control dies, you may put a +1/+1 counter on Knucklebone Witch.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, filter
        ).setTriggerPhrase("Whenever a Goblin you control is put into a graveyard from the battlefield, "));
    }

    private KnuckleboneWitch(final KnuckleboneWitch card) {
        super(card);
    }

    @Override
    public KnuckleboneWitch copy() {
        return new KnuckleboneWitch(this);
    }
}

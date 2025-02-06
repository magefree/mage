package mage.cards.m;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDiscardValue;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
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
public final class MaraudingMako extends CardImpl {

    public MaraudingMako(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you discard one or more cards, put that many +1/+1 counters on this creature.
        this.addAbility(new DiscardOneOrMoreCardsTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), SavedDiscardValue.MANY)
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MaraudingMako(final MaraudingMako card) {
        super(card);
    }

    @Override
    public MaraudingMako copy() {
        return new MaraudingMako(this);
    }
}

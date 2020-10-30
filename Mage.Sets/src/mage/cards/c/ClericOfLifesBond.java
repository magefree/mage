package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.GainLifeFirstTimeTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class ClericOfLifesBond extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another Cleric");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.CLERIC.getPredicate());
    }

    public ClericOfLifesBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Cleric enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // Whenever you gain life for the first time each turn, put a +1/+1 counter on Cleric of Life’s Bond.
        this.addAbility(new GainLifeFirstTimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

    }

    public ClericOfLifesBond(final ClericOfLifesBond card) {
        super(card);
    }

    @Override
    public ClericOfLifesBond copy() {
        return new ClericOfLifesBond(this);
    }
}

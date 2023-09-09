package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.DoubleCounterOnEachPermanentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KalonianHydra extends CardImpl {

    public KalonianHydra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Kalonian Hydra enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(4), true
        ), "with four +1/+1 counters on it"));

        // Whenever Kalonian Hydra attacks, double the number of +1/+1 counters on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(
                new DoubleCounterOnEachPermanentEffect(CounterType.P1P1, StaticFilters.FILTER_CONTROLLED_CREATURE), false
        ));
    }

    private KalonianHydra(final KalonianHydra card) {
        super(card);
    }

    @Override
    public KalonianHydra copy() {
        return new KalonianHydra(this);
    }
}

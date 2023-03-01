package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WatchfulBlisterzoa extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.OIL);

    public WatchfulBlisterzoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Watchful Blisterzoa enters the battlefield with an oil counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                "with an oil counter on it"
        ));

        // When Watchful Blisterzoa dies, draw cards equal to the number of oil counters on it.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(xValue)
                .setText("draw cards equal to the number of oil counters on it")));
    }

    private WatchfulBlisterzoa(final WatchfulBlisterzoa card) {
        super(card);
    }

    @Override
    public WatchfulBlisterzoa copy() {
        return new WatchfulBlisterzoa(this);
    }
}

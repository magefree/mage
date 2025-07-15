package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtmosphericGreenhouse extends CardImpl {

    public AtmosphericGreenhouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{G}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));

        // Station
        this.addAbility(new StationAbility());

        // STATION 8+
        // Flying
        // Trample
        // 5/4
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(TrampleAbility.getInstance())
                .withPT(5, 4));
    }

    private AtmosphericGreenhouse(final AtmosphericGreenhouse card) {
        super(card);
    }

    @Override
    public AtmosphericGreenhouse copy() {
        return new AtmosphericGreenhouse(this);
    }
}

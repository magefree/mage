package mage.cards.s;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class SarcophagusShipOfTheDead extends CardImpl {

    public SarcophagusShipOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true)));

        // Whenever a creature dies, if Sarcophagus isn't a creature, put a charge counter on it.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), false)
            .withInterveningIf(new SourceMatchesFilterCondition("{this} isn't a creature", StaticFilters.FILTER_PERMANENT_NON_CREATURE))
        );

        // Station
        this.addAbility(new StationAbility());

        // STATION 10+
        // Flying
        // 4/4
        this.addAbility(new StationLevelAbility(10).withLevelAbility(FlyingAbility.getInstance()).withPT(4, 4));
    }

    private SarcophagusShipOfTheDead(final SarcophagusShipOfTheDead card) {
        super(card);
    }

    @Override
    public SarcophagusShipOfTheDead copy() {
        return new SarcophagusShipOfTheDead(this);
    }
}

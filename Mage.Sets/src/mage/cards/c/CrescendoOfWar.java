package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class CrescendoOfWar extends CardImpl {

    private static final CountersSourceCount xValue = new CountersSourceCount(CounterType.STRIFE);

    public CrescendoOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of each upkeep, put a strife counter on Crescendo of War.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.STRIFE.createInstance(1), true), TargetController.ANY, false));

        // Attacking creatures get +1/+0 for each strife counter on Crescendo of War.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(xValue, StaticValue.get(0),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_ATTACKING_CREATURES, false)));

        // Blocking creatures you control get +1/+0 for each strife counter on Crescendo of War.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(xValue, StaticValue.get(0),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_BLOCKING_CREATURES, false)));
    }

    private CrescendoOfWar(final CrescendoOfWar card) {
        super(card);
    }

    @Override
    public CrescendoOfWar copy() {
        return new CrescendoOfWar(this);
    }
}


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
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterBlockingCreature;

/**
 *
 * @author fireshoes
 */
public final class CrescendoOfWar extends CardImpl {

    public CrescendoOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // At the beginning of each upkeep, put a strife counter on Crescendo of War.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.STRIFE.createInstance(1), true), TargetController.ANY, false));

        // Attacking creatures get +1/+0 for each strife counter on Crescendo of War.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersSourceCount(CounterType.STRIFE), StaticValue.get(0),
                Duration.WhileOnBattlefield, new FilterAttackingCreature(), false)));

        // Blocking creatures you control get +1/+0 for each strife counter on Crescendo of War.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(new CountersSourceCount(CounterType.STRIFE), StaticValue.get(0),
                Duration.WhileOnBattlefield, new FilterBlockingCreature(), false)));
    }

    private CrescendoOfWar(final CrescendoOfWar card) {
        super(card);
    }

    @Override
    public CrescendoOfWar copy() {
        return new CrescendoOfWar(this);
    }
}

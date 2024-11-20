package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAnyTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class FiveAlarmFire extends CardImpl {

    public FiveAlarmFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // Whenever a creature you control deals combat damage, put a blaze counter on Five-Alarm Fire.
        this.addAbility(new DealsDamageToAnyTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.BLAZE.createInstance()),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                SetTargetPointer.NONE, true, false
        ));

        // Remove five blaze counters from Five-Alarm Fire: Five-Alarm Fire deals 5 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(5, "it"), new RemoveCountersSourceCost(CounterType.BLAZE.createInstance(5)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FiveAlarmFire(final FiveAlarmFire card) {
        super(card);
    }

    @Override
    public FiveAlarmFire copy() {
        return new FiveAlarmFire(this);
    }
}

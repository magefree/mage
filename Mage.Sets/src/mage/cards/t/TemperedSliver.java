package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemperedSliver extends CardImpl {

    public TemperedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have "Whenever this creature deals combat damage to a player, put a +1/+1 counter on it."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)), false,"Whenever this creature deals combat damage to a player, put a +1/+1 counter on it.",false
                ), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private TemperedSliver(final TemperedSliver card) {
        super(card);
    }

    @Override
    public TemperedSliver copy() {
        return new TemperedSliver(this);
    }
}

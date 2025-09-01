package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SphereGrid extends CardImpl {

    public SphereGrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a creature you control deals combat damage to a player, put a +1/+1 counter on that creature.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on that creature"),
                StaticFilters.FILTER_CONTROLLED_CREATURE, false,
                SetTargetPointer.PERMANENT, true
        ));

        // Unlock Ability -- Creatures you control with +1/+1 counters on them have reach and trample.
        Ability ability = new SimpleStaticAbility(new GainAbilityAllEffect(
                ReachAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1
        ).setText("creatures you control with +1/+1 counters on them have reach"));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CONTROLLED_CREATURE_P1P1
        ).setText("and trample"));
        this.addAbility(ability.withFlavorWord("Unlock Ability"));
    }

    private SphereGrid(final SphereGrid card) {
        super(card);
    }

    @Override
    public SphereGrid copy() {
        return new SphereGrid(this);
    }
}

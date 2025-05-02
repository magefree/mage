package mage.cards.d;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DutyBeyondDeath extends CardImpl {

    public DutyBeyondDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_A_CREATURE));

        // Creatures you control gain indestructible until end of turn. Put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
    }

    private DutyBeyondDeath(final DutyBeyondDeath card) {
        super(card);
    }

    @Override
    public DutyBeyondDeath copy() {
        return new DutyBeyondDeath(this);
    }
}

package mage.cards.d;

import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DefendTheCelestus extends CardImpl {

    public DefendTheCelestus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{G}");

        // Distribute three +1/+1 counters among one, two, or three target creatures you control.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(
                CounterType.P1P1, 3, false,
                "one, two, or three target creatures you control"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(
                3, StaticFilters.FILTER_CONTROLLED_CREATURES
        ));
    }

    private DefendTheCelestus(final DefendTheCelestus card) {
        super(card);
    }

    @Override
    public DefendTheCelestus copy() {
        return new DefendTheCelestus(this);
    }
}

package mage.cards.n;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NuclearFallout extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, -2);

    public NuclearFallout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Each creature gets twice -X/-X until end of turn. Each player gets X rad counters.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                xValue, xValue, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_ALL_CREATURES, false,
                "Each creature gets twice -X/-X until end of turn"
        ));
        this.getSpellAbility().addEffect(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(), ManacostVariableValue.REGULAR, TargetController.ANY)
        );
    }

    private NuclearFallout(final NuclearFallout card) {
        super(card);
    }

    @Override
    public NuclearFallout copy() {
        return new NuclearFallout(this);
    }
}
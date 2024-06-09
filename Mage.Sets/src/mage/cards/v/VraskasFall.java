package mage.cards.v;

import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VraskasFall extends CardImpl {

    public VraskasFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Each opponent sacrifices a creature or planeswalker and gets a poison counter.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER
        ));
        this.getSpellAbility().addEffect(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(), TargetController.OPPONENT
        ).setText("and gets a poison counter"));
    }

    private VraskasFall(final VraskasFall card) {
        super(card);
    }

    @Override
    public VraskasFall copy() {
        return new VraskasFall(this);
    }
}

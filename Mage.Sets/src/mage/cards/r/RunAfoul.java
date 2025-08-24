package mage.cards.r;

import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author arcox
 */
public final class RunAfoul extends CardImpl {

    public RunAfoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target opponent sacrifices a creature with flying.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_CREATURE_FLYING, 1, "Target opponent")
                .setText("target opponent sacrifices a creature of their choice with flying"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private RunAfoul(final RunAfoul card) {
        super(card);
    }

    @Override
    public RunAfoul copy() {
        return new RunAfoul(this);
    }
}

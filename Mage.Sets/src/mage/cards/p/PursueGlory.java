package mage.cards.p;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author stravant
 */
public final class PursueGlory extends CardImpl {

    public PursueGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Attacking creatures get +2/+0 until end of turn.
        getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2)));
    }

    private PursueGlory(final PursueGlory card) {
        super(card);
    }

    @Override
    public PursueGlory copy() {
        return new PursueGlory(this);
    }
}

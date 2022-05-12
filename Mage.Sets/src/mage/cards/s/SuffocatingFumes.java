package mage.cards.s;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuffocatingFumes extends CardImpl {

    public SuffocatingFumes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new GenericManaCost(2)));
    }

    private SuffocatingFumes(final SuffocatingFumes card) {
        super(card);
    }

    @Override
    public SuffocatingFumes copy() {
        return new SuffocatingFumes(this);
    }
}

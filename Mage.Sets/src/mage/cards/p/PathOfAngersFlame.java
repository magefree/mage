
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class PathOfAngersFlame extends CardImpl {

    public PathOfAngersFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.subtype.add(SubType.ARCANE);

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));
    }

    private PathOfAngersFlame(final PathOfAngersFlame card) {
        super(card);
    }

    @Override
    public PathOfAngersFlame copy() {
        return new PathOfAngersFlame(this);
    }
}

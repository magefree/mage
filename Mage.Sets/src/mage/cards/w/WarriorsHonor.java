
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class WarriorsHonor extends CardImpl {

    public WarriorsHonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));
    }

    private WarriorsHonor(final WarriorsHonor card) {
        super(card);
    }

    @Override
    public WarriorsHonor copy() {
        return new WarriorsHonor(this);
    }
}

package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class Rally extends CardImpl {

    public Rally(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{W}");

        // Blocking creatures get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_BLOCKING_CREATURES, false));
    }

    private Rally(final Rally card) {
        super(card);
    }

    @Override
    public Rally copy() {
        return new Rally(this);
    }
}

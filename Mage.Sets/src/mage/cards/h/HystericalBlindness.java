package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class HystericalBlindness extends CardImpl {

    public HystericalBlindness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Creatures your opponents control get -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-4, 0, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false));
    }

    private HystericalBlindness(final HystericalBlindness card) {
        super(card);
    }

    @Override
    public HystericalBlindness copy() {
        return new HystericalBlindness(this);
    }
}

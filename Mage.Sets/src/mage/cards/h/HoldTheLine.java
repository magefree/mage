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
 * @author LevelX
 */
public final class HoldTheLine extends CardImpl {

    public HoldTheLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");

        // Blocking creatures get +7/+7 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(7, 7, Duration.EndOfTurn, StaticFilters.FILTER_BLOCKING_CREATURES, false));
    }

    private HoldTheLine(final HoldTheLine card) {
        super(card);
    }

    @Override
    public HoldTheLine copy() {
        return new HoldTheLine(this);
    }
}

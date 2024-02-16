package mage.cards.t;

import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBlackBreath extends CardImpl {

    public TheBlackBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Creatures your opponents control get -1/-1 until end of turn. The Ring tempts you.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        ));
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private TheBlackBreath(final TheBlackBreath card) {
        super(card);
    }

    @Override
    public TheBlackBreath copy() {
        return new TheBlackBreath(this);
    }
}

package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeekersFolly extends CardImpl {

    public SeekersFolly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one --
        // * Target opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false
        )));
    }

    private SeekersFolly(final SeekersFolly card) {
        super(card);
    }

    @Override
    public SeekersFolly copy() {
        return new SeekersFolly(this);
    }
}

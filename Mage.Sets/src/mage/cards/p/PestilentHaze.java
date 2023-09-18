package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestilentHaze extends CardImpl {

    public PestilentHaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Choose one —
        // • All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));

        // • Remove two loyalty counters from each planeswalker.
        this.getSpellAbility().addMode(new Mode(new PestilentHazeEffect()));
    }

    private PestilentHaze(final PestilentHaze card) {
        super(card);
    }

    @Override
    public PestilentHaze copy() {
        return new PestilentHaze(this);
    }
}

class PestilentHazeEffect extends OneShotEffect {

    PestilentHazeEffect() {
        super(Outcome.Benefit);
        staticText = "remove two loyalty counters from each planeswalker";
    }

    private PestilentHazeEffect(final PestilentHazeEffect effect) {
        super(effect);
    }

    @Override
    public PestilentHazeEffect copy() {
        return new PestilentHazeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_PLANESWALKER,
                        source.getControllerId(), source, game
                ).stream()
                .forEach(permanent -> permanent.removeCounters(CounterType.LOYALTY.createInstance(2), source, game));
        return true;
    }
}

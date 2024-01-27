package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class SuspectSourceEffect extends OneShotEffect {

    public SuspectSourceEffect() {
        super(Outcome.Benefit);
        staticText = "suspect it";
    }

    private SuspectSourceEffect(final SuspectSourceEffect effect) {
        super(effect);
    }

    @Override
    public SuspectSourceEffect copy() {
        return new SuspectSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.setSuspected(true, game, source));
        return true;
    }
}


package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Jmlundeen
 */
public class HarnessSourceEffect extends OneShotEffect {

    public HarnessSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Harness {this}. <i>(Once harnessed, its ∞ ability is active.)<i>";
    }

    protected HarnessSourceEffect(final HarnessSourceEffect effect) {
        super(effect);
    }

    @Override
    public HarnessSourceEffect copy() {
        return new HarnessSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.setHarnessed(true);
        return true;
    }
}

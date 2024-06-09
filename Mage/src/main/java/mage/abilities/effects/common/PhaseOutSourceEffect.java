package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author fireshoes
 */
public class PhaseOutSourceEffect extends OneShotEffect {

    public PhaseOutSourceEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} phases out";
    }

    protected PhaseOutSourceEffect(final PhaseOutSourceEffect effect) {
        super(effect);
    }

    @Override
    public PhaseOutSourceEffect copy() {
        return new PhaseOutSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            return permanent.phaseOut(game);
        }
        return false;
    }
}

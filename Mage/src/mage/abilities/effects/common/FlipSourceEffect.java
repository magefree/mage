package mage.abilities.effects.common;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 * @author Loki
 */
public class FlipSourceEffect extends OneShotEffect<FlipSourceEffect> {

    public FlipSourceEffect() {
        super(Constants.Outcome.BecomeCreature);
        staticText = "flip it";
    }

    public FlipSourceEffect(final FlipSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            return p.flip(game);
        }
        return false;
    }

    @Override
    public FlipSourceEffect copy() {
        return new FlipSourceEffect(this);
    }

}

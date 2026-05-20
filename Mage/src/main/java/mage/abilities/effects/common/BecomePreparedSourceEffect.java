package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class BecomePreparedSourceEffect extends OneShotEffect {

    public BecomePreparedSourceEffect() {
        super(Outcome.Benefit);
        staticText = "{this} becomes prepared";
    }

    private BecomePreparedSourceEffect(final BecomePreparedSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomePreparedSourceEffect copy() {
        return new BecomePreparedSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.setPrepared(true, game);
        return true;
    }
}

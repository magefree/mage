package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class GoadAttachedEffect extends ContinuousEffectImpl {

    public GoadAttachedEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "and is goaded";
    }

    private GoadAttachedEffect(final GoadAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent attached = game.getPermanent(permanent.getAttachedTo());
        if (attached == null) {
            return false;
        }
        attached.addGoadingPlayer(source.getControllerId());
        return true;
    }

    @Override
    public GoadAttachedEffect copy() {
        return new GoadAttachedEffect(this);
    }
}

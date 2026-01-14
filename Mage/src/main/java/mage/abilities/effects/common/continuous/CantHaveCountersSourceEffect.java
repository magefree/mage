package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Styxo
 */
public class CantHaveCountersSourceEffect extends ContinuousEffectImpl {

    public CantHaveCountersSourceEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "{this} can't have counters put on it";
    }

    protected CantHaveCountersSourceEffect(final CantHaveCountersSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantHaveCountersSourceEffect copy() {
        return new CantHaveCountersSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            permanent = source.getSourcePermanentIfItStillExists(game);
        }
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.setCountersCanBeAdded(false);
        return true;
    }
}

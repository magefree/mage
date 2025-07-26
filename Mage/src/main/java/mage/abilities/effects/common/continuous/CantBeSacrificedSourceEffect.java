package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * @author xenohedron
 */
public class CantBeSacrificedSourceEffect extends ContinuousEffectImpl {

    public CantBeSacrificedSourceEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} can't be sacrificed";
    }

    protected CantBeSacrificedSourceEffect(final CantBeSacrificedSourceEffect effect) {
        super(effect);
    }

    @Override
    public CantBeSacrificedSourceEffect copy() {
        return new CantBeSacrificedSourceEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Permanent) object).setCanBeSacrificed(false);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            permanent = source.getSourcePermanentIfItStillExists(game);
        }
        if (permanent == null) {
            discard();
            return false;
        }
        affectedObjects.add(permanent);
        return true;
    }
}

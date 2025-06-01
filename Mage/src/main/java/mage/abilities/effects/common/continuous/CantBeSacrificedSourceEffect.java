package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            permanent.setCanBeSacrificed(false);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            permanent = source.getSourcePermanentIfItStillExists(game);
        }
        return permanent != null ? Collections.singletonList(permanent) : Collections.emptyList();
    }

    @Override
    public CantBeSacrificedSourceEffect copy() {
        return new CantBeSacrificedSourceEffect(this);
    }
}

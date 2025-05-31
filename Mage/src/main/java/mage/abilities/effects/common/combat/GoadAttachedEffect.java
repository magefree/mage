package mage.abilities.effects.common.combat;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        return false;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && permanent.getAttachedTo() != null) {
            Permanent attached = game.getPermanent(permanent.getAttachedTo());
            return attached != null ? Collections.singletonList(attached) : Collections.emptyList();
        }
        return Collections.emptyList();
    }

    @Override
    public GoadAttachedEffect copy() {
        return new GoadAttachedEffect(this);
    }
}

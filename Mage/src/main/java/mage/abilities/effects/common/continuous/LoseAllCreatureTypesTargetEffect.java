package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author emerald000
 */
public class LoseAllCreatureTypesTargetEffect extends ContinuousEffectImpl {

    public LoseAllCreatureTypesTargetEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
    }

    protected LoseAllCreatureTypesTargetEffect(final LoseAllCreatureTypesTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseAllCreatureTypesTargetEffect copy() {
        return new LoseAllCreatureTypesTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.removeAllCreatureTypes(game);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "target " + mode.getTargets().get(0).getTargetName() + " loses all creature types " + duration.toString();
    }
}

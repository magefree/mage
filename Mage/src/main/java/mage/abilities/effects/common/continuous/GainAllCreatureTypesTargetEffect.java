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

import java.util.UUID;

/**
 * @author TheElk801
 */
public class GainAllCreatureTypesTargetEffect extends ContinuousEffectImpl {

    public GainAllCreatureTypesTargetEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
    }

    protected GainAllCreatureTypesTargetEffect(final GainAllCreatureTypesTargetEffect effect) {
        super(effect);
    }

    @Override
    public GainAllCreatureTypesTargetEffect copy() {
        return new GainAllCreatureTypesTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null) {
                target.setIsAllCreatureTypes(game, true);
                affectedTargets++;
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "target " + mode.getTargets().get(0).getTargetName() + " gains all creature types " + duration.toString();
    }
}

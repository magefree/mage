

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
 * @author nantuko
 */
public class LoseAllAbilitiesTargetEffect extends ContinuousEffectImpl {

    public LoseAllAbilitiesTargetEffect(Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    protected LoseAllAbilitiesTargetEffect(final LoseAllAbilitiesTargetEffect effect) {
        super(effect);
    }

    @Override
    public LoseAllAbilitiesTargetEffect copy() {
        return new LoseAllAbilitiesTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                permanent.removeAllAbilities(source.getSourceId(), game);
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
        return getTargetPointer().describeTargets(mode.getTargets(), "it")
                + " loses all abilities " + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }

}

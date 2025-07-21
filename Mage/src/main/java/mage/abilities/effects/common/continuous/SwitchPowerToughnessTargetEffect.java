package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author ayratn
 */
public class SwitchPowerToughnessTargetEffect extends ContinuousEffectImpl {

    public SwitchPowerToughnessTargetEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, Outcome.BoostCreature);
    }

    protected SwitchPowerToughnessTargetEffect(final SwitchPowerToughnessTargetEffect effect) {
        super(effect);
    }

    @Override
    public SwitchPowerToughnessTargetEffect copy() {
        return new SwitchPowerToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID uuid : getTargetPointer().getTargets(game, source)) {
            Permanent target = game.getPermanent(uuid);
            if (target != null) {
                target.switchPowerToughness();
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
        return "switch " + getTargetPointer().describeTargets(mode.getTargets(), "that creature") + "'s power and toughness" +
                ' ' + duration.toString();
    }
}

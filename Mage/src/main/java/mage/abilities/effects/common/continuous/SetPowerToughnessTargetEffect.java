

package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SetPowerToughnessTargetEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;

    public SetPowerToughnessTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
    }

    public SetPowerToughnessTargetEffect(int power, int toughness, Duration duration) {
        this(new StaticValue(power), new StaticValue(toughness), duration);
    }

    public SetPowerToughnessTargetEffect(final SetPowerToughnessTargetEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public SetPowerToughnessTargetEffect copy() {
        return new SetPowerToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID targetId: this.getTargetPointer().getTargets(game, source)) {
            Permanent target = game.getPermanent(targetId);
            if (target != null) {
                if (power != null) {
                    target.getPower().setValue(power.calculate(game, source, this));
                }
                if (toughness != null) {
                    target.getToughness().setValue(toughness.calculate(game, source, this));
                }
                result = true;
            }
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("target ").append(mode.getTargets().get(0).getTargetName()).append(" has base power and toughness ");
        sb.append(power).append('/').append(toughness).append(' ').append(duration.toString());
        return sb.toString();
    }


}

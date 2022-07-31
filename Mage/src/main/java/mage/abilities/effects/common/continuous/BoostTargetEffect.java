package mage.abilities.effects.common.continuous;

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
import mage.target.Target;
import mage.util.CardUtil;

import java.util.Locale;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostTargetEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;

    public BoostTargetEffect(int power, int toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration);
    }

    /**
     * @param power
     * @param toughness
     * @param duration
     */
    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, CardUtil.getBoostOutcome(power, toughness));
        this.power = power;
        this.toughness = toughness;
    }

    public BoostTargetEffect(final BoostTargetEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
    }

    @Override
    public BoostTargetEffect copy() {
        return new BoostTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null && target.isCreature(game)) {
                target.addPower(power.calculate(game, source, this));
                target.addToughness(toughness.calculate(game, source, this));
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
        StringBuilder sb = new StringBuilder();
        if (mode == null || mode.getTargets().isEmpty()) {
            sb.append("it gets ");
        } else {
            Target target = mode.getTargets().get(0);
            if (target.getMaxNumberOfTargets() > 1) {
                if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                    sb.append("up to ");
                }
                sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append(" get ");
            } else {
                if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                    sb.append("up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(' ');
                }
                if (!target.getTargetName().toLowerCase(Locale.ENGLISH).startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(target.getTargetName()).append(" gets ");
            }
        }
        sb.append(CardUtil.getBoostText(power, toughness, duration));
        return sb.toString();
    }
}

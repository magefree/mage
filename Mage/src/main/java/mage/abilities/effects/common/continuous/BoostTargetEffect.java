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
    private final boolean lockedIn;

    public BoostTargetEffect(int power, int toughness) {
        this(power, toughness, Duration.EndOfTurn);
    }

    public BoostTargetEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, false);
    }

    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    /**
     * @param power
     * @param toughness
     * @param duration
     * @param lockedIn  if true, power and toughness will be calculated only
     *                  once, when the ability resolves
     */
    public BoostTargetEffect(DynamicValue power, DynamicValue toughness, Duration duration, boolean lockedIn) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, isCanKill(toughness) ? Outcome.UnboostCreature : Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.lockedIn = lockedIn;
    }

    public BoostTargetEffect(final BoostTargetEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostTargetEffect copy() {
        return new BoostTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (lockedIn) {
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent target = game.getPermanent(permanentId);
            if (target != null && target.isCreature()) {
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
        if (mode == null || mode.getTargets().isEmpty()) {
            return "no target";
        }
        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder();
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName()).append(" get ");
        } else {
            if (!target.getTargetName().toLowerCase(Locale.ENGLISH).startsWith("another")) {
                sb.append("target ");
            }
            sb.append(target.getTargetName()).append(" gets ");
        }
        String p = power.toString();
        if (!p.startsWith("-")) {
            sb.append('+');
        }
        sb.append(p).append('/');
        String t = toughness.toString();
        if (!t.startsWith("-")) {
            if (t.equals("0") && p.startsWith("-")) {
                sb.append('-');
            } else {
                sb.append('+');
            }
        }
        sb.append(t);
        if (duration != Duration.WhileOnBattlefield) {
            sb.append(' ').append(duration.toString());
        }
        String message = null;
        String fixedPart = null;
        if (t.contains("X")) {
            message = toughness.getMessage();
            fixedPart = ", where X is ";
        } else if (p.contains("X")) {
            message = power.getMessage();
            fixedPart = ", where X is ";
        } else if (!power.getMessage().isEmpty()) {
            message = power.getMessage();
            fixedPart = " for each ";
        } else if (!toughness.getMessage().isEmpty()) {
            message = toughness.getMessage();
            fixedPart = " for each ";
        }
        if (message != null && !message.isEmpty() && fixedPart != null) {
            sb.append(fixedPart).append(message);
        }
        return sb.toString();
    }
}

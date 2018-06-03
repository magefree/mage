
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com, North
 */
public class BoostEnchantedEffect extends ContinuousEffectImpl {

    private DynamicValue power;
    private DynamicValue toughness;
    private boolean lockedIn = false;

    public BoostEnchantedEffect(int power, int toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(int power, int toughness, Duration duration) {
        this(new StaticValue(power), new StaticValue(toughness), duration);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness) {
        this(power, toughness, Duration.WhileOnBattlefield);
    }

    public BoostEnchantedEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, isCanKill(toughness) ? Outcome.UnboostCreature : Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        setText();
    }

    public BoostEnchantedEffect(final BoostEnchantedEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostEnchantedEffect copy() {
        return new BoostEnchantedEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (lockedIn) {
            power = new StaticValue(power.calculate(game, source, this));
            toughness = new StaticValue(toughness.calculate(game, source, this));
        }
        if (affectedObjectsSet) {
            // Added boosts of activated or triggered abilities exist independent from the source they are created by
            // so a continuous effect for the permanent itself with the attachment is created
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanent(equipment.getAttachedTo());
            }
        }
        if (permanent != null) {
            permanent.addPower(power.calculate(game, source, this));
            permanent.addToughness(toughness.calculate(game, source, this));
        }
        return true;
    }

    public void setLockedIn(boolean lockedIn) {
        this.lockedIn = lockedIn;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("enchanted creature gets ");
        String p = power.toString();
        if (!p.startsWith("-")) {
            sb.append('+');
        }
        sb.append(p).append('/');
        String t = toughness.toString();
        if (!t.startsWith("-")) {
            if (p.startsWith("-")) {
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
        staticText = sb.toString();
    }

}

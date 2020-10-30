package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
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
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BoostSourceEffect extends ContinuousEffectImpl implements SourceEffect {
    private DynamicValue power;
    private DynamicValue toughness;
    private final boolean lockedIn;

    public BoostSourceEffect(int power, int toughness, Duration duration) {
        this(StaticValue.get(power), StaticValue.get(toughness), duration, false);
    }

    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration) {
        this(power, toughness, duration, false);
    }

    /**
     * @param power
     * @param toughness
     * @param duration
     * @param lockedIn  if true, power and toughness will be calculated only once, when the ability resolves
     */
    public BoostSourceEffect(DynamicValue power, DynamicValue toughness, Duration duration, boolean lockedIn) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
        this.lockedIn = lockedIn;
        setText();
    }

    public BoostSourceEffect(final BoostSourceEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.lockedIn = effect.lockedIn;
    }

    @Override
    public BoostSourceEffect copy() {
        return new BoostSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            try {
                affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BoostSourceEffect.class).error("Could not get sourceId reference: " + source.getRule());
            }
        }
        if (lockedIn) {
            power = StaticValue.get(power.calculate(game, source, this));
            toughness = StaticValue.get(toughness.calculate(game, source, this));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target;
        if (affectedObjectsSet) {
            target = affectedObjectList.get(0).getPermanent(game);
        } else {
            target = game.getPermanent(source.getSourceId());
        }
        if (target != null) {
            target.addPower(power.calculate(game, source, this));
            target.addToughness(toughness.calculate(game, source, this));
            return true;
        }
        return false;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("{this} gets ");
        String p = power.toString();
        if (!p.startsWith("-")) {
            sb.append('+');
        }
        sb.append(p).append('/');
        String t = toughness.toString();
        if (!t.startsWith("-")) {
            sb.append('+');
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

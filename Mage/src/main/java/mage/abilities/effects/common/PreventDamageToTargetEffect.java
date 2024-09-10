package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PreventDamageToTargetEffect extends PreventionEffectImpl {

    public PreventDamageToTargetEffect(Duration duration) {
        this(duration, false);
    }

    public PreventDamageToTargetEffect(Duration duration, boolean onlyCombat) {
        this(duration, Integer.MAX_VALUE, onlyCombat);
    }

    public PreventDamageToTargetEffect(Duration duration, int amount) {
        this(duration, amount, false);
    }

    public PreventDamageToTargetEffect(Duration duration, int amount, boolean onlyCombat) {
        super(duration, amount, onlyCombat);
    }

    public PreventDamageToTargetEffect(Duration duration, boolean onlyCombat, boolean consumable, DynamicValue amountToPreventDynamic) {
        super(duration, 0, onlyCombat, consumable, amountToPreventDynamic);
    }

    protected PreventDamageToTargetEffect(final PreventDamageToTargetEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageToTargetEffect copy() {
        return new PreventDamageToTargetEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used
                && super.applies(event, source, game)
                && getTargetPointer().getTargets(game, source).contains(event.getTargetId());
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("prevent all damage that would be dealt to ");
        } else {
            sb.append("prevent the next ").append(amountToPrevent).append(" damage that would be dealt to ");
        }
        sb.append(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn");
            } else {
                sb.append(duration.toString());
            }
        }
        return sb.toString();
    }

}

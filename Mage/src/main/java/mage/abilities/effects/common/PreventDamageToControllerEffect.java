
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

public class PreventDamageToControllerEffect extends PreventionEffectImpl {

    public PreventDamageToControllerEffect(Duration duration) {
        this(duration, false, false, Integer.MAX_VALUE);
    }

    public PreventDamageToControllerEffect(Duration duration, int amountToPrevent) {
        this(duration, false, true, amountToPrevent);
    }

    public PreventDamageToControllerEffect(Duration duration, boolean onlyCombat, boolean consumable, int amountToPrevent) {
        super(duration, amountToPrevent, onlyCombat, consumable, null);
        staticText = setText();
    }

    public PreventDamageToControllerEffect(Duration duration, boolean onlyCombat, boolean consumable, DynamicValue amountToPreventDynamic) {
        super(duration, 0, onlyCombat, consumable, amountToPreventDynamic);
        staticText = setText();
    }

    protected PreventDamageToControllerEffect(final PreventDamageToControllerEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageToControllerEffect copy() {
        return new PreventDamageToControllerEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())) {
                return true;
            }

        }
        return false;
    }

    private String setText() {
        // Prevent the next X damage that would be dealt to you this turn
        StringBuilder sb = new StringBuilder("prevent ");
        if (amountToPrevent == Integer.MAX_VALUE) {
            sb.append("all ");
        } else if (amountToPreventDynamic != null) {
            sb.append("the next ").append(amountToPreventDynamic.toString()).append(' ');
        } else {
            sb.append("the next ").append(amountToPrevent).append(' ');
        }
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to you");
        if (duration == Duration.EndOfTurn) {
            sb.append(" this turn");
        }
        return sb.toString();
    }
}

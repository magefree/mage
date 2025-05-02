
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PreventionEffectImpl extends ReplacementEffectImpl implements PreventionEffect {

    protected DynamicValue amountToPreventDynamic;
    protected int amountToPrevent;
    protected final boolean onlyCombat;
    protected boolean consumable;

    protected PreventionEffectImpl(Duration duration) {
        this(duration, Integer.MAX_VALUE, false);
    }

    protected PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat) {
        this(duration, amountToPrevent, onlyCombat, true);
    }

    protected PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat, boolean consumable) {
        this(duration, amountToPrevent, onlyCombat, consumable, null);
    }

    /**
     * @param duration
     * @param amountToPrevent
     * @param onlyCombat
     * @param consumable
     * @param amountToPreventDynamic if set, on init amountToPrevent is set to
     *                               calculated value of amountToPreventDynamic
     */
    protected PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat, boolean consumable, DynamicValue amountToPreventDynamic) {
        super(duration, Outcome.PreventDamage);
        this.effectType = EffectType.PREVENTION;
        this.amountToPrevent = amountToPrevent;
        this.amountToPreventDynamic = amountToPreventDynamic;
        this.onlyCombat = onlyCombat;
        this.consumable = consumable;
    }

    protected PreventionEffectImpl(final PreventionEffectImpl effect) {
        super(effect);
        this.amountToPrevent = effect.amountToPrevent;
        this.amountToPreventDynamic = effect.amountToPreventDynamic;
        this.onlyCombat = effect.onlyCombat;
        this.consumable = effect.consumable;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (amountToPreventDynamic != null) {
            amountToPrevent = amountToPreventDynamic.calculate(game, source, this);
        }
    }

    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = game.preventDamage(event, source, game, amountToPrevent);
        if (!preventionData.isError() && !preventionData.isReplaced()) {
            if (consumable) {
                amountToPrevent = preventionData.getRemainingAmount();
            }
            if (amountToPrevent == 0) {
                this.discard();
            }
        }
        return preventionData;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        // damage amount is reduced or set to 0 so complete replacement of damage event is never neccessary
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getFlag() && (!onlyCombat || ((DamageEvent) event).isCombatDamage());
    }

}

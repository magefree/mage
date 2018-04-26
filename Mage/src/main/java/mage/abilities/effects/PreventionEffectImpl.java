/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
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
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class PreventionEffectImpl extends ReplacementEffectImpl implements PreventionEffect {

    protected DynamicValue amountToPreventDynamic;
    protected int amountToPrevent;
    protected final boolean onlyCombat;
    protected boolean consumable;

    public PreventionEffectImpl(Duration duration) {
        this(duration, Integer.MAX_VALUE, false);
    }

    public PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat) {
        this(duration, amountToPrevent, onlyCombat, true);
    }

    public PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat, boolean consumable) {
        this(duration, amountToPrevent, onlyCombat, consumable, null);
    }

    /**
     *
     * @param duration
     * @param amountToPrevent
     * @param onlyCombat
     * @param consumable
     * @param amountToPreventDynamic if set, on init amountToPrevent is set to
     * calculated value of amountToPreventDynamic
     */
    public PreventionEffectImpl(Duration duration, int amountToPrevent, boolean onlyCombat, boolean consumable, DynamicValue amountToPreventDynamic) {
        super(duration, Outcome.PreventDamage);
        this.effectType = EffectType.PREVENTION;
        this.amountToPrevent = amountToPrevent;
        this.amountToPreventDynamic = amountToPreventDynamic;
        this.onlyCombat = onlyCombat;
        this.consumable = consumable;
    }

    public PreventionEffectImpl(final PreventionEffectImpl effect) {
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

    @Override
    public boolean apply(Game game, Ability source) {
        // not used for prevention effect
        return true;
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
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getFlag() && (!onlyCombat || ((DamageEvent) event).isCombatDamage());
    }

}

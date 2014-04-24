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

import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;


/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public abstract class PreventionEffectImpl<T extends PreventionEffectImpl<T>> extends ReplacementEffectImpl<T> implements PreventionEffect<T> {
   
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
        super(duration, Outcome.PreventDamage);
        this.effectType = EffectType.PREVENTION;
        this.amountToPrevent = amountToPrevent;
        this.onlyCombat = onlyCombat;
    }

    public PreventionEffectImpl(final PreventionEffectImpl effect) {
        super(effect);
        this.amountToPrevent = effect.amountToPrevent;
        this.onlyCombat = effect.onlyCombat;
        this.consumable = effect.consumable;
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
                this.used = true;
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
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                // return preventable flag && combatOnly check
                return event.getFlag() && (!onlyCombat || ((DamageEvent)event).isCombatDamage());
            default:
                return false;
        }
    }

}

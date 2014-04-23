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

package mage.abilities.effects.common;

import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;

import java.util.UUID;

/**
 * @author nantuko
 */
public class PreventDamageByTargetEffect extends PreventionEffectImpl<PreventDamageByTargetEffect> {

    public PreventDamageByTargetEffect(Duration duration, int amount) {
        super(duration, amount, false);
    }

    public PreventDamageByTargetEffect(Duration duration, boolean all) {
        super(duration, Integer.MAX_VALUE, false);
    }

    public PreventDamageByTargetEffect(final PreventDamageByTargetEffect effect) {
        super(effect);
    }

    @Override
    public PreventDamageByTargetEffect copy() {
        return new PreventDamageByTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        for (Target target : source.getTargets()) {
            for (UUID chosen : target.getTargets()) {
                if (event.getSourceId().equals(chosen)) {
                    preventDamage(event, source, chosen, game);
                }
            }
        }
        return false;
    }

    private void preventDamage(GameEvent event, Ability source, UUID target, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, target, source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            if (amountToPrevent == Integer.MAX_VALUE) {
                int damage = event.getAmount();
                event.setAmount(0);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getId(), source.getControllerId(), damage));
            } else {
                if (event.getAmount() >= amountToPrevent) {
                    int damage = amountToPrevent;
                    event.setAmount(event.getAmount() - amountToPrevent);
                    this.used = true;
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getId(), source.getControllerId(), damage));
                } else {
                    int damage = event.getAmount();
                    event.setAmount(0);
                    amountToPrevent -= damage;
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, target, source.getId(), source.getControllerId(), damage));
                }
            }
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            for (Target target : source.getTargets()) {
                for (UUID chosen : target.getTargets()) {
                    if (event.getSourceId().equals(chosen)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (amountToPrevent == Integer.MAX_VALUE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Prevent all damage target ");
            sb.append(mode.getTargets().get(0).getTargetName()).append(" would deal ").append(duration.toString());
            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Prevent the next ").append(amountToPrevent).append(" damage that ");
            sb.append(mode.getTargets().get(0).getTargetName()).append(" would deal ").append(duration.toString());
            return sb.toString();
        }

    }

}

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

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public class PreventAllDamageByAllPermanentsEffect extends PreventionEffectImpl {

    private FilterPermanent filter;

    public PreventAllDamageByAllPermanentsEffect(Duration duration) {
        this(null, duration, false);
    }

    public PreventAllDamageByAllPermanentsEffect(Duration duration, boolean onlyCombat) {
        this(null, duration, onlyCombat);
    }

    public PreventAllDamageByAllPermanentsEffect(FilterCreaturePermanent filter, Duration duration, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.filter = filter;
    }

    public PreventAllDamageByAllPermanentsEffect(final PreventAllDamageByAllPermanentsEffect effect) {
        super(effect);
        if (effect.filter != null) {
            this.filter = effect.filter.copy();
        }
    }

    @Override
    public PreventAllDamageByAllPermanentsEffect copy() {
        return new PreventAllDamageByAllPermanentsEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage() || !onlyCombat) {
                if (filter == null) {
                    return true;
                }
                Permanent permanent = game.getPermanent(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage ");
        sb.append(duration.toString());
        if (filter != null) {
            sb.append(" dealt by ");
            sb.append(filter.getMessage());
        }
        return sb.toString();
    }
}

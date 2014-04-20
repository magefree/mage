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
import mage.abilities.effects.PreventionEffectImpl;
import mage.filter.FilterInPlay;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class PreventAllNonCombatDamageToEffect extends PreventionEffectImpl<PreventAllNonCombatDamageToEffect> {

    protected FilterInPlay filter;

    public PreventAllNonCombatDamageToEffect(Duration duration, FilterInPlay filter) {
        super(duration, Integer.MAX_VALUE);
        this.filter = filter;
        staticText = "Prevent all non combat damage that would be dealt to " + filter.getMessage() + " " + duration.toString();
    }

    public PreventAllNonCombatDamageToEffect(final PreventAllNonCombatDamageToEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public PreventAllNonCombatDamageToEffect copy() {
        return new PreventAllNonCombatDamageToEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)
                && !((DamageEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
            else {
                Player player = game.getPlayer(event.getTargetId());
                if (player != null && filter.match(player, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

}

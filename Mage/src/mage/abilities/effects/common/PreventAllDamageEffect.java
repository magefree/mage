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

import mage.Constants.Duration;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX
 */
public class PreventAllDamageEffect extends PreventionEffectImpl<PreventAllDamageEffect> {

        private FilterPermanent filter;
        private Boolean onlyCombat;

        public PreventAllDamageEffect(FilterPermanent filter, Duration duration, Boolean onlyCombat) {
                    super(duration);
                    this.filter = filter;
                    this.onlyCombat = onlyCombat;
            }
        public PreventAllDamageEffect(Duration duration, Boolean onlyCombat) {
                    super(duration);
                    this.onlyCombat = onlyCombat;
            }

        public PreventAllDamageEffect(Duration duration) {
                    super(duration);
                    this.onlyCombat = false;
            }

        public PreventAllDamageEffect(final PreventAllDamageEffect effect) {
                super(effect);
                if (effect.filter != null)
                        this.filter = effect.filter.copy();
                this.onlyCombat = effect.onlyCombat;
        }

	@Override
	public PreventAllDamageEffect copy() {
		return new PreventAllDamageEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
		if (!game.replaceEvent(preventEvent)) {
			int damage = event.getAmount();
                        Permanent permanent = game.getPermanent(event.getSourceId());
                        StringBuilder sourceName = new StringBuilder();
                        if (permanent != null) {
                                sourceName.append(" from ").append(permanent.getName());
                        }
                        sourceName.insert(0, "Damage").append(" has been prevented: ").append(damage);
			event.setAmount(0);
                        game.informPlayers(sourceName.toString());
//			game.informPlayers("Damage" + sourceName + " has been prevented: " + damage);
			game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
		}
		return false;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage() || !onlyCombat) {
                if (filter == null)
                    return true;
                Permanent permanent = game.getPermanent(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent))
                    return true;
            }
        }
		return false;
	}

        @Override
	public String getText(Mode mode) {
                StringBuilder sb = new StringBuilder("Prevent all ");
                if (onlyCombat)
                        sb.append("combat ");
                sb.append("damage ");
                sb.append(duration.toString());
                if (filter != null) {
                        sb.append(" dealt by ");
                        sb.append(filter.getMessage());
                }
                return sb.toString();
	}
}

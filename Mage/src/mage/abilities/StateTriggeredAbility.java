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

package mage.abilities;

import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.effects.Effect;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StateTriggeredAbility<T extends StateTriggeredAbility<T>> extends TriggeredAbilityImpl<T> {

	public StateTriggeredAbility(Zone zone, Effect effect) {
		super(zone, effect);
	}

	public StateTriggeredAbility(final StateTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public void trigger(Game game, UUID controllerId) {
		//20100716 - 603.8
		Boolean triggered = (Boolean) game.getState().getValue(this.id.toString() + "triggered");
		if (triggered == null)
			triggered = Boolean.FALSE;
		if (!triggered) {
			game.getState().setValue(this.id.toString() + "triggered", Boolean.TRUE);
			super.trigger(game, controllerId);
		}
	}

	@Override
	public boolean resolve(Game game) {
		//20100716 - 603.8
		game.getState().setValue(this.id.toString() + "triggered", Boolean.FALSE);
		return super.resolve(game);
	}

	public void counter(Game game) {
		game.getState().setValue(this.id.toString() + "triggered", Boolean.FALSE);
	}
}

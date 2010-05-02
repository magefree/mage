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

import java.util.ArrayList;
import java.util.Iterator;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DelayedTriggeredAbilities extends ArrayList<DelayedTriggeredAbility> {

	public void checkTriggers(GameEvent event, Game game) {
		Iterator<DelayedTriggeredAbility> it = this.iterator();
		while (it.hasNext()) {
			DelayedTriggeredAbility ability = it.next();
			if (ability.checkTrigger(event, game))
				it.remove();
		}
	}

//	public boolean check(Game game) {
//		boolean played = false;
//		Iterator<TriggeredAbility> it = this.iterator();
//		while(it.hasNext()) {
//			TriggeredAbility ability = it.next();
//			it.remove();
//			played |= game.getPlayer(ability.getControllerId()).triggerAbility(ability, game);
//		}
//		return played;
//	}

//	public TriggeredAbilities getControlledBy(UUID controllerId) {
//		TriggeredAbilities controlledBy = new TriggeredAbilities();
//		for (TriggeredAbility ability: this) {
//			if (ability.getControllerId().equals(controllerId))
//				controlledBy.add(ability);
//		}
//		return controlledBy;
//	}

}


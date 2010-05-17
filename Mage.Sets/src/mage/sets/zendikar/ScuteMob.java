/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AddPlusOneCountersSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.sets.Zendikar;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ScuteMob extends CardImpl {

	public ScuteMob(UUID ownerId) {
		super(ownerId, "Scute Mob", new CardType[]{CardType.CREATURE}, "{G}");
		this.expansionSetId = Zendikar.getInstance().getId();
		this.color.setGreen(true);
		this.subtype.add("Insect");
		this.art = "123606_typ_reg_sty_010.jpg";
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);
		this.addAbility(new ScuteMobAbility());
	}

}

class ScuteMobAbility extends TriggeredAbilityImpl {

	private FilterLandPermanent filter = new FilterLandPermanent();

	public ScuteMobAbility() {
		super(Zone.BATTLEFIELD, new AddPlusOneCountersSourceEffect(4));
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
			trigger(game, this.controllerId);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkIfClause(Game game) {
		return game.getBattlefield().countAll(filter, this.controllerId) >= 5;
	}

	@Override
	public String getRule() {
		return "At the beginning of your upkeep, if you control five or more lands, put four +1/+1 counters on Scute Mob.";
	}
}
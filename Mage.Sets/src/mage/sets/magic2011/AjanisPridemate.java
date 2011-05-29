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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class AjanisPridemate extends CardImpl<AjanisPridemate> {

	public AjanisPridemate(UUID ownerId) {
		super(ownerId, 3, "Ajani's Pridemate", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "1}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Cat");
		this.subtype.add("Soldier");
		this.color.setWhite(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		this.addAbility(new AjanisPridemateAbility());
	}

	public AjanisPridemate(final AjanisPridemate card) {
		super(card);
	}

	@Override
	public AjanisPridemate copy() {
		return new AjanisPridemate(this);
	}

}

class AjanisPridemateAbility extends TriggeredAbilityImpl<AjanisPridemateAbility> {

	public AjanisPridemateAbility() {
		super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
	}

	public AjanisPridemateAbility(final AjanisPridemateAbility ability) {
		super(ability);
	}

	@Override
	public AjanisPridemateAbility copy() {
		return new AjanisPridemateAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.GAINED_LIFE && event.getPlayerId().equals(controllerId)) {
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever you gain life, you may put a +1/+1 counter on Ajani's Pridemate.";
	}

}
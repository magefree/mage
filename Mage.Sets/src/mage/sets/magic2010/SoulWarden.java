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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SoulWarden extends CardImpl<SoulWarden> {

	public SoulWarden(UUID ownerId) {
		super(ownerId, 34, "Soul Warden", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
		this.expansionSetCode = "M10";
		this.subtype.add("Human");
		this.subtype.add("Cleric");
		this.color.setWhite(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);
		this.addAbility(new SoulWardenAbility());
	}

	public SoulWarden(final SoulWarden card) {
		super(card);
	}

	@Override
	public SoulWarden copy() {
		return new SoulWarden(this);
	}

}

class SoulWardenAbility extends TriggeredAbilityImpl<SoulWardenAbility> {

	public SoulWardenAbility() {
		super(Zone.BATTLEFIELD, new GainLifeEffect(1));
	}

	public SoulWardenAbility(final SoulWardenAbility ability) {
		super(ability);
	}

	@Override
	public SoulWardenAbility copy() {
		return new SoulWardenAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Zone.BATTLEFIELD) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent.getCardType().contains(CardType.CREATURE) && !permanent.getId().equals(this.getSourceId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever another creature enters the battlefield, " + super.getRule();
	}

}

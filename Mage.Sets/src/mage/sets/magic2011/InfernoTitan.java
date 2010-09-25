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
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BoostSourceEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class InfernoTitan extends CardImpl<InfernoTitan> {

	public InfernoTitan(UUID ownerId) {
		super(ownerId, "Inferno Titan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
		this.expansionSetCode = "M11";
		this.subtype.add("Giant");
		this.color.setRed(true);
		this.power = new MageInt(6);
		this.toughness = new MageInt(6);

		this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
		this.addAbility(new InfernoTitanAbility());
	}

	public InfernoTitan(final InfernoTitan card) {
		super(card);
	}

	@Override
	public InfernoTitan copy() {
		return new InfernoTitan(this);
	}

	@Override
	public String getArt() {
		return "129131_typ_reg_sty_010.jpg";
	}

}

class InfernoTitanAbility extends TriggeredAbilityImpl<InfernoTitanAbility> {

	public InfernoTitanAbility() {
		super(Zone.BATTLEFIELD, new DamageMultiEffect(3), false);
	}

	public InfernoTitanAbility(final InfernoTitanAbility ability) {
		super(ability);
	}

	@Override
	public InfernoTitanAbility copy() {
		return new InfernoTitanAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
			trigger(game, event.getPlayerId());
			return true;
		}
		if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId()) ) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getToZone() == Zone.BATTLEFIELD) {
				trigger(game, event.getPlayerId());
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three target creatures and/or players.";
	}

}

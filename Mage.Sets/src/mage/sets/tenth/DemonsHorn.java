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

package mage.sets.tenth;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DemonsHorn extends CardImpl<DemonsHorn> {

	public DemonsHorn(UUID ownerId) {
		super(ownerId, 320, "Demon's Horn", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
		this.expansionSetCode = "10E";
		this.addAbility(new DemonsHornAbility());
	}

	public DemonsHorn(final DemonsHorn card) {
		super(card);
	}

	@Override
	public DemonsHorn copy() {
		return new DemonsHorn(this);
	}

	@Override
	public String getArt() {
		return "75220_typ_reg_sty_010.jpg";
	}

}

class DemonsHornAbility extends TriggeredAbilityImpl<DemonsHornAbility> {

	public DemonsHornAbility() {
		super(Zone.BATTLEFIELD, new GainLifeEffect(1), true);
	}

	public DemonsHornAbility(final DemonsHornAbility ability) {
		super(ability);
	}

	@Override
	public DemonsHornAbility copy() {
		return new DemonsHornAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST) {
			MageObject spell = game.getObject(event.getTargetId());
			if (spell != null && spell.getColor().isBlack()) {
				trigger(game, event.getPlayerId());
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever a player casts a black spell, you may gain 1 life.";
	}

}

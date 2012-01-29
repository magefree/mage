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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continious.PlayAdditionalLandsAllEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public class RitesOfFlourishing extends CardImpl<RitesOfFlourishing> {

	public RitesOfFlourishing(UUID ownerId) {
		super(ownerId, 192, "Rites of Flourishing", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
		this.expansionSetCode = "M12";

		this.color.setGreen(true);

		// At the beginning of each player's draw step, that player draws an additional card.
		this.addAbility(new RitesOfFlourishingAbility());

		// Each player may play an additional land on each of his or her turns.
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsAllEffect()));
	}

	public RitesOfFlourishing(final RitesOfFlourishing card) {
		super(card);
	}

	@Override
	public RitesOfFlourishing copy() {
		return new RitesOfFlourishing(this);
	}
}

class RitesOfFlourishingAbility extends TriggeredAbilityImpl<RitesOfFlourishingAbility> {

	public RitesOfFlourishingAbility() {
		super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1));
	}

	public RitesOfFlourishingAbility(final RitesOfFlourishingAbility ability) {
		super(ability);
	}

	@Override
	public RitesOfFlourishingAbility copy() {
		return new RitesOfFlourishingAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.DRAW_STEP_PRE) {
			this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
			return true;
		}
		return false;
	}

	@Override
	public boolean checkInterveningIfClause(Game game) {
		return !game.getPermanent(this.sourceId).isTapped();
	}

	@Override
	public String getRule() {
		return "At the beginning of each player's draw step, that player draws an additional card.";
	}

}

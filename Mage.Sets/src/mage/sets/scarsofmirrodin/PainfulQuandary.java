/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PainfulQuandary extends CardImpl<PainfulQuandary> {

	public PainfulQuandary(UUID ownerId) {
		super(ownerId, 73, "Painful Quandary", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
		this.expansionSetCode = "SOM";
		this.color.setBlack(true);
		this.addAbility(new PainfulQuandryAbility());
	}

	public PainfulQuandary(final PainfulQuandary card) {
		super(card);
	}

	@Override
	public PainfulQuandary copy() {
		return new PainfulQuandary(this);
	}

}

class PainfulQuandryAbility extends TriggeredAbilityImpl<PainfulQuandryAbility> {

	public PainfulQuandryAbility() {
		super(Zone.BATTLEFIELD, new PainfulQuandryEffect());
	}
	
	public PainfulQuandryAbility(final PainfulQuandryAbility ability) {
		super(ability);
	}
	
	@Override
	public PainfulQuandryAbility copy() {
		return new PainfulQuandryAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.SPELL_CAST && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
			return true;
		}
		return false;
	}
	
	@Override
	public String getRule() {
		return "Whenever an opponent casts a spell, that player loses 5 life unless he or she discards a card.";
	}
}

class PainfulQuandryEffect extends OneShotEffect<PainfulQuandryEffect> {

	public PainfulQuandryEffect() {
		super(Outcome.Damage);
		staticText = "player loses 5 life unless he or she discards a card";
	}

	public PainfulQuandryEffect(final PainfulQuandryEffect effect) {
		super(effect);
	}

	@Override
	public PainfulQuandryEffect copy() {
		return new PainfulQuandryEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(targetPointer.getFirst(source));
		if (player != null) {
			Cost cost = new DiscardTargetCost(new TargetCardInHand());
			if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
				player.loseLife(5, game);
			}
			return true;
		}
		return false;
	}

}

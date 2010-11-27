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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class WildEvocation extends CardImpl<WildEvocation> {

	public WildEvocation(UUID ownerId) {
		super(ownerId, 160, "Wild Evocation", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");
		this.expansionSetCode = "M11";
		this.color.setRed(true);
		this.addAbility(new WildEvocationAbility());
	}

	public WildEvocation(final WildEvocation card) {
		super(card);
	}

	@Override
	public WildEvocation copy() {
		return new WildEvocation(this);
	}

	@Override
	public String getArt() {
		return "123721_typ_reg_sty_010.jpg";
	}

}

class WildEvocationAbility extends TriggeredAbilityImpl<WildEvocationAbility> {

	public WildEvocationAbility() {
		super(Zone.BATTLEFIELD, new WildEvocationEffect());
	}

	public WildEvocationAbility(final WildEvocationAbility ability) {
		super(ability);
	}

	@Override
	public WildEvocationAbility copy() {
		return new WildEvocationAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.DRAW_STEP_PRE) {
			this.addTarget(new TargetPlayer());
			this.targets.get(0).add(event.getPlayerId(), game);
			trigger(game, this.controllerId);
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "At the beginning of each player's upkeep, that player reveals a card at random from his or her hand. If it's a land card, the player puts it onto the battlefield. Otherwise, the player casts it without paying its mana cost if able.";
	}

}

class WildEvocationEffect extends OneShotEffect<WildEvocationEffect> {

	public WildEvocationEffect() {
		super(Outcome.PutCardInPlay);
	}

	public WildEvocationEffect(final WildEvocationEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null && player.getHand().size() > 0) {
			Card card = player.getHand().getRandom(game);
			Cards cards = new CardsImpl();
			cards.add(card);
			player.revealCards(cards, game);
			if (card.getCardType().contains(CardType.LAND)) {
				card.putOntoBattlefield(game, Zone.HAND, source.getId(), player.getId());
			}
			else {
				player.cast(card.getSpellAbility(), game, true);
			}
			return true;
		}
		return false;
	}

	@Override
	public WildEvocationEffect copy() {
		return new WildEvocationEffect(this);
	}

}
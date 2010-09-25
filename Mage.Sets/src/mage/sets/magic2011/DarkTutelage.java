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
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DarkTutelage extends CardImpl<DarkTutelage> {

	public DarkTutelage(UUID ownerId) {
		super(ownerId, "DarkTutelage", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
		this.expansionSetCode = "M11";
		this.getColor().setBlack(true);
		this.addAbility(new OnEventTriggeredAbility(EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new DarkTutelageEffect(), false));
	}

	public DarkTutelage(final DarkTutelage card) {
		super(card);
	}

	@Override
	public DarkTutelage copy() {
		return new DarkTutelage(this);
	}

	@Override
	public String getArt() {
		return "129074_typ_reg_sty_010.jpg";
	}

}

class DarkTutelageEffect extends OneShotEffect<DarkTutelageEffect> {

	public DarkTutelageEffect() {
		super(Outcome.DrawCard);
	}

	public DarkTutelageEffect(final DarkTutelageEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Card card = player.getLibrary().removeFromTop(game);
		if (card != null) {
			player.putInHand(card, game);
			player.loseLife(card.getManaCost().convertedManaCost(), game);
			Cards cards = new CardsImpl();
			cards.add(card);
			player.revealCards(cards, game);
			return true;
		}
		return false;
	}

	@Override
	public DarkTutelageEffect copy() {
		return new DarkTutelageEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost";
	}

}

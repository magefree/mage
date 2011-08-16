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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 * @author nantuko
 */
public class Ponder extends CardImpl<Ponder> {

	public Ponder(UUID ownerId) {
		super(ownerId, 68, "Ponder", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{U}");
		this.expansionSetCode = "M10";

		this.color.setBlue(true);

		// Look at the top three cards of your library, then put them back in any order. You may shuffle your library.
		this.getSpellAbility().addEffect(new PonderEffect());
		// Draw a card.
		this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
	}

	public Ponder(final Ponder card) {
		super(card);
	}

	@Override
	public Ponder copy() {
		return new Ponder(this);
	}
}

class PonderEffect extends OneShotEffect<PonderEffect> {

	protected static FilterCard filter = new FilterCard("card to put on the top of your library");

	public PonderEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "Look at the top three cards of your library, then put them back in any order. You may shuffle your library";
	}

	public PonderEffect(final PonderEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Cards cards = new CardsImpl(Constants.Zone.PICK);
		int count = Math.min(3, player.getLibrary().size());
		if (count == 0) {
			return false;
		}
		for (int i = 0; i < count; i++) {
			Card card = player.getLibrary().removeFromTop(game);
			if (card != null) {
				cards.add(card);
			}
			game.setZone(card.getId(), Constants.Zone.PICK);
		}
		if (cards.size() > 1) {
			TargetCard target2 = new TargetCard(Constants.Zone.PICK, filter);
			target2.setRequired(true);
			while (cards.size() > 1) {
				player.choose(Constants.Outcome.Benefit, cards, target2, game);
				Card card = cards.get(target2.getFirstTarget(), game);
				if (card != null) {
					cards.remove(card);
					card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
				}
				target2.clearChosen();
			}
		}
		if (cards.size() == 1) {
			Card card = cards.get(cards.iterator().next(), game);
			card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
		}
		if (player.chooseUse(Constants.Outcome.Benefit, "Shuffle you library?", game)) {
			player.shuffleLibrary(game);
		}
		return true;
	}

	@Override
	public PonderEffect copy() {
		return new PonderEffect(this);
	}

}

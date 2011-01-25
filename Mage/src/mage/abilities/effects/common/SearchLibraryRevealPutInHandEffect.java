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

package mage.abilities.effects.common;

import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearchLibraryRevealPutInHandEffect extends SearchEffect<SearchLibraryRevealPutInHandEffect> {

	public SearchLibraryRevealPutInHandEffect(TargetCardInLibrary target) {
		super(target, Outcome.DrawCard);
	}

	public SearchLibraryRevealPutInHandEffect(final SearchLibraryRevealPutInHandEffect effect) {
		super(effect);
	}

	@Override
	public SearchLibraryRevealPutInHandEffect copy() {
		return new SearchLibraryRevealPutInHandEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		player.searchLibrary(target, game);
		if (target.getTargets().size() > 0) {
			Cards revealed = new CardsImpl();
			for (UUID cardId: (List<UUID>)target.getTargets()) {
				Card card = player.getLibrary().remove(cardId, game);
				if (card != null) {
					card.moveToZone(Zone.HAND, source.getId(), game, false);
					revealed.add(card);
				}
			}
			player.shuffleLibrary(game);
			player.revealCards("Search", revealed, game);
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		StringBuilder sb = new StringBuilder();
		sb.append("Search your library for ");
		if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
			sb.append("up to ").append(target.getMaxNumberOfTargets()).append(" ");
			sb.append(target.getTargetName()).append(", reveal them, and put them into your hand");
		}
		else {
			sb.append("a ").append(target.getTargetName()).append(", reveal that card, and put it into your hand");
		}
		sb.append(". Then shuffle your library");

		return sb.toString();
	}

//	@Override
//	public void setSource(Ability ability) {
//		super.setSource(ability);
//		target.setAbility(ability);
//	}

}

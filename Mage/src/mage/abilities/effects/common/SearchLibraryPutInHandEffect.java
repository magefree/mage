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
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LokiX, BetaSteward_at_googlemail.com
 */
public class SearchLibraryPutInHandEffect extends SearchEffect<SearchLibraryPutInHandEffect> {

    public SearchLibraryPutInHandEffect(TargetCardInLibrary target) {
		super(target, Outcome.DrawCard);
    }

	public SearchLibraryPutInHandEffect(final SearchLibraryPutInHandEffect effect) {
		super(effect);
	}

	@Override
	public SearchLibraryPutInHandEffect copy() {
		return new SearchLibraryPutInHandEffect(this);
	}

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
		player.searchLibrary(target, game);
        if (target.getTargets().size() > 0) {
            for (UUID cardId: (List<UUID>)target.getTargets()) {
                Card card = player.getLibrary().remove(cardId, game);
                if (card != null){
                    player.putInHand(card, game);
                }
            }
            player.shuffleLibrary(game);
        }
        return true;
    }

    @Override
    public String getText(Ability source) {
        StringBuilder sb = new StringBuilder();
        sb.append("Search your library for ");
        if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
			sb.append("up to ").append(target.getMaxNumberOfTargets()).append(" ");
			sb.append(target.getTargetName()).append(" and put them into your hand");
		}
		else {
			sb.append("a ").append(target.getTargetName()).append(" and put that card into your hand");
		}
		sb.append(". Then shuffle your library");

		return sb.toString();
    }

//    @Override
//    public void setSource(Ability ability) {
//		super.setSource(ability);
//		target.setAbility(ability);
//    }
}

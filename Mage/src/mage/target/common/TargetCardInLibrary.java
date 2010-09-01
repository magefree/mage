/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.target.common;

import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInLibrary extends TargetCard<TargetCardInLibrary> {

	public TargetCardInLibrary() {
		this(1, 1, new FilterCard());
	}

	public TargetCardInLibrary(FilterCard filter) {
		this(1, 1, filter);
	}

	public TargetCardInLibrary(int numTargets, FilterCard filter) {
		this(numTargets, numTargets, filter);
	}

	public TargetCardInLibrary(int minNumTargets, int maxNumTargets, FilterCard filter) {
		super(minNumTargets, maxNumTargets, Zone.LIBRARY, filter);
	}

	public TargetCardInLibrary(final TargetCardInLibrary target) {
		super(target);
	}

	@Override
	public boolean choose(Outcome outcome, UUID playerId, Ability source, Game game) {
		Player player = game.getPlayer(playerId);
		while (!isChosen() && !doneChosing()) {
			chosen = targets.size() >= minNumberOfTargets;
			if (!player.chooseTarget(new CardsImpl(Zone.LIBRARY, player.getLibrary().getCards(game)), this, null, game)) {
				return chosen;
			}
			chosen = targets.size() >= minNumberOfTargets;
		}
		while (!doneChosing()) {
			if (!player.chooseTarget(new CardsImpl(Zone.LIBRARY, player.getLibrary().getCards(game)), this, null, game)) {
				break;
			}
		}
		return chosen = true;
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		Card card = game.getPlayer(source.getControllerId()).getLibrary().getCard(id, game);
		if (card != null)
			return filter.match(card);
		return false;
	}

	@Override
	public TargetCardInLibrary copy() {
		return new TargetCardInLibrary(this);
	}

}

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

package mage.target;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.Zone;
import mage.cards.Card;
import mage.cards.Cards;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCard<T extends TargetCard<T>> extends TargetObject<TargetCard<T>> {

	protected FilterCard filter;

	protected TargetCard(Zone zone) {
		this(1, 1, zone, new FilterCard());
	}
	
	public TargetCard(Zone zone, FilterCard filter) {
		this(1, 1, zone, filter);
	}

	public TargetCard(int numTargets, Zone zone, FilterCard filter) {
		this(numTargets, numTargets, zone, filter);
	}

	public TargetCard(int minNumTargets, int maxNumTargets, Zone zone, FilterCard filter) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = zone;
		this.filter = filter;
		this.targetName = filter.getMessage();
	}

	public TargetCard(final TargetCard target) {
		super(target);
		this.filter = target.filter.copy();
	}

	@Override
	public FilterCard getFilter() {
		return this.filter;
	}

	/**
	 * Checks if there are enough {@link Card} that can be chosen.
	 *
	 * @param sourceId - the target event source
	 * @param sourceControllerId - controller of the target event source
	 * @param game
	 * @return - true if enough valid {@link Card} exist
	 */
	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		return canChoose(sourceControllerId, game);
	}

	/**
	 * Checks if there are enough {@link Card} that can be selected.
	 *
	 * @param sourceControllerId - controller of the select event
	 * @param game
	 * @return - true if enough valid {@link Card} exist
	 */
	@Override
	public boolean canChoose(UUID sourceControllerId, Game game) {
		for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
			if (filter.matchOwner(playerId)) {
				Player player = game.getPlayer(playerId);
				if (player != null) {
					switch (zone) {
						case HAND:
							if (player.getHand().count(filter, player.getId(), game) >= this.minNumberOfTargets)
								return true;
							break;
						case GRAVEYARD:
							if (player.getGraveyard().count(filter, player.getId(), game) >= this.minNumberOfTargets)
								return true;
							break;
						case LIBRARY:
							if (player.getLibrary().count(filter, game) >= this.minNumberOfTargets)
								return true;
							break;
                        case EXILED:
                            if (game.getExile().getPermanentExile().count(filter, player.getId(), game) >= this.minNumberOfTargets)
                                return true;
                            break;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
		return possibleTargets(sourceControllerId, game);
	}

	@Override
	public Set<UUID> possibleTargets(UUID sourceControllerId, Game game) {
		Set<UUID> possibleTargets = new HashSet<UUID>();
		for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
			if (filter.matchOwner(playerId)) {
				Player player = game.getPlayer(playerId);
				if (player != null) {
					switch (zone) {
						case HAND:
							for (Card card: player.getHand().getCards(filter, game)) {
								possibleTargets.add(card.getId());
							}
							break;
						case GRAVEYARD:
							for (Card card: player.getGraveyard().getCards(filter, game)) {
								possibleTargets.add(card.getId());
							}
							break;
						case LIBRARY:
							for (Card card: player.getLibrary().getUniqueCards(game)) {
								if (filter.match(card))
									possibleTargets.add(card.getId());
							}
							break;
						case EXILED:
							for (Card card: game.getExile().getPermanentExile().getUniqueCards(game)) {
								if (filter.match(card, player.getId(), game))
									possibleTargets.add(card.getId());
							}
							break;
					}
				}
			}
		}
		return possibleTargets;
	}

	public boolean canTarget(UUID id, Cards cards, Game game) {
		Card card = cards.get(id, game);
		if (card != null)
			return filter.match(card);
		return false;
	}

	@Override
	public TargetCard copy() {
		return new TargetCard(this);
	}

}

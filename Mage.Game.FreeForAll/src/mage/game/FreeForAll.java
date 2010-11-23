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

package mage.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FreeForAll extends GameImpl<FreeForAll> {

	private int numPlayers;
	private List<UUID> mulliganed = new ArrayList<UUID>();

	public FreeForAll(MultiplayerAttackOption attackOption, RangeOfInfluence range) {
		super(attackOption, range);
	}

	public FreeForAll(final FreeForAll game) {
		super(game);
		this.numPlayers = game.numPlayers;
		for (UUID playerId: game.mulliganed) {
			mulliganed.add(playerId);
		}
	}

	@Override
	public GameType getGameType() {
		return new FreeForAllType();
	}

	@Override
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	@Override
	public int getLife() {
		return 20;
	}

	@Override
	public Set<UUID> getOpponents(UUID playerId) {
		Set<UUID> opponents = new HashSet<UUID>();
		for (UUID opponentId: this.getPlayer(playerId).getInRange()) {
			if (!opponentId.equals(playerId))
				opponents.add(opponentId);
		}
		return opponents;
	}

	@Override
	public void mulligan(UUID playerId) {
		Player player = getPlayer(playerId);
		int numCards = player.getHand().size();
		//record first mulligan and increment card count
		if (!mulliganed.contains(playerId)) {
			numCards += 1;
			mulliganed.add(playerId);
		}
		player.getLibrary().addAll(player.getHand().getCards(this), this);
		player.getHand().clear();
		player.shuffleLibrary(this);
		player.drawCards(numCards - 1, this);
		fireInformEvent(player.getName() + " mulligans down to " + Integer.toString(numCards - 1) + " cards");
	}

	@Override
	public FreeForAll copy() {
		return new FreeForAll(this);
	}

}

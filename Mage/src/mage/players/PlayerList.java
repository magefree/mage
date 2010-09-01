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

package mage.players;

import java.util.UUID;
import mage.game.Game;
import mage.util.CircularList;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PlayerList extends CircularList<UUID> {

	public PlayerList() {}

	public PlayerList(final PlayerList list) {
		super(list);
	}

	public Player getNext(Game game) {
		Player player;
		UUID start = this.get();
		while (true) {
			player = game.getPlayer(super.getNext());
			if (!player.hasLeft() && !player.hasLost())
				break;
			if (player.getId().equals(start))
				return null;
		}
		return player;
	}

	public Player getPrevious(Game game) {
		Player player;
		UUID start = this.get();
		while (true) {
			player = game.getPlayer(super.getPrevious());
			if (!player.hasLeft() && !player.hasLost())
				break;
			if (player.getId().equals(start))
				return null;
		}
		return player;
	}

	@Override
	public PlayerList copy() {
		return new PlayerList(this);
	}

}

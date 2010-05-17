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

import java.util.UUID;
import mage.Constants.Zone;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetPlayer extends TargetImpl {

	protected FilterPlayer filter = new FilterPlayer();

	public TargetPlayer() {
		this(1, 1);
	}

	public TargetPlayer(int numTargets) {
		this(numTargets, numTargets);
	}

	public TargetPlayer(int minNumTargets, int maxNumTargets) {
		this.minNumberOfTargets = minNumTargets;
		this.maxNumberOfTargets = maxNumTargets;
		this.zone = Zone.PLAYER;
		this.targetName = "player";
	}

	@Override
	public FilterPlayer getFilter() {
		return filter;
	}

	@Override
	public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
		int count = 0;
		for (UUID playerId: game.getPlayer(sourceControllerId).getInRange()) {
			Player player = game.getPlayer(playerId);
			if (player != null && !player.hasLeft() && filter.match(player)) {
				if (player.canTarget(game.getObject(sourceId)))
					count++;
			}
		}
		if (count >= this.minNumberOfTargets)
			return true;
		return false;
	}

	@Override
	public boolean isLegal(Game game) {
		for (UUID playerId: targets) {
			if (!canTarget(playerId, game))
				return false;
		}
		return true;
	}

	@Override
	public boolean canTarget(UUID id, Game game) {
		Player player = game.getPlayer(id);
		if (player != null) {
			if (source != null)
				return player.canTarget(game.getObject(this.source.getSourceId())) && filter.match(player);
			else 
				return filter.match(player);
		}
		return false;
	}

	@Override
	public String getTargetedName(Game game) {
		StringBuilder sb = new StringBuilder();
		for (UUID targetId: getTargets()) {
			sb.append(game.getPlayer(targetId).getName()).append(" ");
		}
		return sb.toString();
	}

}

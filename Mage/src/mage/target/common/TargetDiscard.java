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
import mage.Constants.Zone;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetDiscard extends TargetCard {

	private UUID playerId;

	public TargetDiscard(UUID playerId) {
		this(1, 1, new FilterCard(), playerId);
	}

	public TargetDiscard(FilterCard filter, UUID playerId) {
		this(1, 1, filter, playerId);
	}

	public TargetDiscard(int numTargets, FilterCard filter, UUID playerId) {
		this(numTargets, numTargets, filter, playerId);
	}

	public TargetDiscard(int minNumTargets, int maxNumTargets, FilterCard filter, UUID playerId) {
		super(minNumTargets, maxNumTargets, Zone.HAND, filter);
		this.filter.getOwnerId().add(playerId);
		this.playerId = playerId;
		this.required = true;
		this.targetName = "card to discard";
	}

	@Override
	public boolean canTarget(UUID id, Game game) {
		Card card = game.getPlayer(playerId).getHand().get(id);
		if (card != null)
			return filter.match(card);
		return false;
	}

}

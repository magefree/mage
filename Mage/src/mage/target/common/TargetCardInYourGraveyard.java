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

package mage.target.common;

import java.util.UUID;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TargetCardInYourGraveyard extends TargetCard<TargetCardInYourGraveyard> {

	public TargetCardInYourGraveyard() {
		this(1, 1, new FilterCard("card from your graveyard"));
	}

	public TargetCardInYourGraveyard(FilterCard filter) {
		this(1, 1, filter);
	}

	public TargetCardInYourGraveyard(int numTargets, FilterCard filter) {
		this(numTargets, numTargets, filter);
	}

	public TargetCardInYourGraveyard(int minNumTargets, int maxNumTargets, FilterCard filter) {
		super(minNumTargets, maxNumTargets, Zone.GRAVEYARD, filter);
		this.targetName = filter.getMessage();
	}

	public TargetCardInYourGraveyard(final TargetCardInYourGraveyard target) {
		super(target);
	}

	@Override
	public boolean canTarget(UUID id, Ability source, Game game) {
		Card card = game.getCard(id);
		if (card != null && game.getZone(card.getId()) == Zone.GRAVEYARD)
			if (game.getPlayer(source.getControllerId()).getGraveyard().contains(id))
				return filter.match(card);
		return false;
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
		if (game.getPlayer(sourceControllerId).getGraveyard().count(filter, game) >= this.minNumberOfTargets)
			return true;
		return false;
	}

	@Override
	public TargetCardInYourGraveyard copy() {
		return new TargetCardInYourGraveyard(this);
	}

}

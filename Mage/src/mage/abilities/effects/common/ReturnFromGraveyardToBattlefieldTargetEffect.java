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

import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect<ReturnFromGraveyardToBattlefieldTargetEffect> {

	private boolean tapped;

	public ReturnFromGraveyardToBattlefieldTargetEffect() {
		this(false);
	}

	public ReturnFromGraveyardToBattlefieldTargetEffect(boolean tapped) {
		super(Outcome.PutCreatureInPlay);
		this.tapped = tapped;
	}

	public ReturnFromGraveyardToBattlefieldTargetEffect(final ReturnFromGraveyardToBattlefieldTargetEffect effect) {
		super(effect);
		this.tapped = effect.tapped;
	}

	@Override
	public ReturnFromGraveyardToBattlefieldTargetEffect copy() {
		return new ReturnFromGraveyardToBattlefieldTargetEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Card card = game.getCard(source.getFirstTarget());
		if (card != null) {
			Player player = game.getPlayer(card.getOwnerId());
			if (player != null) {
				player.removeFromGraveyard(card, game);
				if (card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId())) {
					if (tapped) {
						Permanent permanent = game.getPermanent(card.getId());
						if (permanent != null)
							permanent.setTapped(true);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getText(Mode mode) {
		StringBuilder sb = new StringBuilder();
		sb.append("Put target ").append(mode.getTargets().get(0).getTargetName()).append(" onto the battlefield");
		if (tapped)
			sb.append(" tapped");
		sb.append(" under your control");
		return sb.toString();
	}

}

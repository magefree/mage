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

package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PathToExile extends CardImpl<PathToExile> {

	public PathToExile(UUID ownerId) {
		super(ownerId, 15, "Path to Exile", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
		this.expansionSetCode = "CON";
		this.color.setWhite(true);
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
		this.getSpellAbility().addEffect(new PathToExileEffect());
	}

	public PathToExile(final PathToExile card) {
		super(card);
	}

	@Override
	public PathToExile copy() {
		return new PathToExile(this);
	}
}

class PathToExileEffect extends OneShotEffect {

	public PathToExileEffect() {
		super(Outcome.Exile);
		staticText = "Exile target creature. Its controller may search his or her library for a basic land card, put that card onto the battlefield tapped, then shuffle his or her library";
	}

	public PathToExileEffect(final PathToExileEffect effect) {
		super(effect);
	}

	@Override
	public PathToExileEffect copy() {
		return new PathToExileEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			Player player = game.getPlayer(permanent.getControllerId());
			if (permanent.moveToZone(Zone.EXILED, source.getId(), game, false)) {
				if (player.chooseUse(Outcome.PutCardInPlay, "Use Path to Exile effect?", game)) {
					TargetCardInLibrary target = new TargetCardInLibrary(new FilterBasicLandCard());
					player.searchLibrary(target, game);
					Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
					if (card != null) {
						if (card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), permanent.getControllerId())) {
							Permanent land = game.getPermanent(card.getId());
							if (land != null)
								land.setTapped(true);
						}
					}
					player.shuffleLibrary(game);
				}
				return true;
			}
		}
		return false;
	}

}
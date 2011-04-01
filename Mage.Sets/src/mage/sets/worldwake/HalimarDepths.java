/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HalimarDepths extends CardImpl<HalimarDepths> {

	public HalimarDepths(UUID ownerId) {
		super(ownerId, 137, "Halimar Depths", Rarity.COMMON, new CardType[]{CardType.LAND}, null);
		this.expansionSetCode = "WWK";
        this.addAbility(new EntersBattlefieldTappedAbility());
		this.addAbility(new EntersBattlefieldTriggeredAbility(new HalimarDepthsEffect()));
		this.addAbility(new BlueManaAbility());
	}

	public HalimarDepths(final HalimarDepths card) {
		super(card);
	}

	@Override
	public HalimarDepths copy() {
		return new HalimarDepths(this);
	}

}

class HalimarDepthsEffect extends OneShotEffect<HalimarDepthsEffect> {

	protected static FilterCard filter2 = new FilterCard("card to put on the top of your library");

	public HalimarDepthsEffect() {
		super(Outcome.Benefit);
	}

	public HalimarDepthsEffect(final HalimarDepthsEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Cards cards = new CardsImpl(Zone.PICK);
		for (int i = 0; i < 3; i++) {
			Card card = player.getLibrary().removeFromTop(game);
			cards.add(card);
			game.setZone(card.getId(), Zone.PICK);
		}
		if (cards.size() > 1) {
			TargetCard target2 = new TargetCard(Zone.PICK, filter2);
			target2.setRequired(true);
			while (cards.size() > 1) {
				player.choose(Outcome.Detriment, cards, target2, game);
				Card card = cards.get(target2.getFirstTarget(), game);
				cards.remove(card);
				card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
				target2.clearChosen();
			}
		}
		if (cards.size() == 1) {
			Card card = cards.get(cards.iterator().next(), game);
			card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
		}
		return true;
	}

	@Override
	public HalimarDepthsEffect copy() {
		return new HalimarDepthsEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "look at the top three cards of your library, then put them back in any order";
	}

}

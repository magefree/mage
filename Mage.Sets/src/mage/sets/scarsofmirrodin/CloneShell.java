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
package mage.sets.scarsofmirrodin;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class CloneShell extends CardImpl<CloneShell> {

	public CloneShell(UUID ownerId) {
		super(ownerId, 143, "Clone Shell", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Shapeshifter");

		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		// Imprint - When Clone Shell enters the battlefield, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library in any order.
		this.addAbility(new EntersBattlefieldTriggeredAbility(new CloneShellEffect(), false));

		// When Clone Shell dies, turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control.
		this.addAbility(new DiesTriggeredAbility(new CloneShellDiesEffect()));
	}

	public CloneShell(final CloneShell card) {
		super(card);
	}

	@Override
	public CloneShell copy() {
		return new CloneShell(this);
	}
}

class CloneShellEffect extends OneShotEffect<CloneShellEffect> {

	protected static FilterCard filter1 = new FilterCard("card to exile face down");
	protected static FilterCard filter2 = new FilterCard("card to put on the bottom of your library");

	public CloneShellEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library in any order";
	}

	public CloneShellEffect(CloneShellEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Cards cards = new CardsImpl(Constants.Zone.PICK);
		int count = Math.min(player.getLibrary().size(), 4);
		for (int i = 0; i < count; i++) {
			Card card = player.getLibrary().removeFromTop(game);
			cards.add(card);
			game.setZone(card.getId(), Constants.Zone.PICK);
		}

		if (cards.size() == 0) {
			return false;

		}
		TargetCard target1 = new TargetCard(Constants.Zone.PICK, filter1);
		if (player.choose(Constants.Outcome.Detriment, cards, target1, game)) {
			Card card = cards.get(target1.getFirstTarget(), game);
			if (card != null) {
				cards.remove(card);
				card.setFaceDown(true);
				card.moveToExile(getId(), "Clone Shell (Imprint)", source.getSourceId(), game);
				Permanent permanent = game.getPermanent(source.getSourceId());
				if (permanent != null) {
					permanent.imprint(card.getId(), game);
				}
			}
			target1.clearChosen();
		}

		if (cards.size() > 0) {
			TargetCard target2 = new TargetCard(Constants.Zone.PICK, filter2);
			target2.setRequired(true);
			while (cards.size() > 1) {
				player.choose(Constants.Outcome.Benefit, cards, target2, game);
				Card card = cards.get(target2.getFirstTarget(), game);
				if (card != null) {
					cards.remove(card);
					card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, false);
				}
				target2.clearChosen();
			}
			Card card = cards.get(cards.iterator().next(), game);
			card.moveToZone(Constants.Zone.LIBRARY, source.getId(), game, true);
		}

		return true;
	}

	@Override
	public CloneShellEffect copy() {
		return new CloneShellEffect(this);
	}

}

class CloneShellDiesEffect extends OneShotEffect<CloneShellDiesEffect> {

	public CloneShellDiesEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "turn the exiled card face up. If it's a creature card, put it onto the battlefield under your control";
	}

	public CloneShellDiesEffect(CloneShellDiesEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
		if (permanent != null) {
			List<UUID> imprinted = permanent.getImprinted();
			if (imprinted.size() > 0) {
				Card imprintedCard = game.getCard(imprinted.get(0));
				imprintedCard.setFaceDown(false);
				if (imprintedCard.getCardType().contains(CardType.CREATURE)) {
					imprintedCard.putOntoBattlefield(game, Constants.Zone.EXILED, source.getSourceId(), source.getControllerId());
				}
			}
		}

		return true;
	}

	@Override
	public CloneShellDiesEffect copy() {
		return new CloneShellDiesEffect(this);
	}

}

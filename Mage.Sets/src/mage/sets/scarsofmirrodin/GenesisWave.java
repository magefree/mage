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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.Filter.ComparisonType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GenesisWave extends CardImpl<GenesisWave> {

	public GenesisWave(UUID ownerId) {
		super(ownerId, 122, "Genesis Wave", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}{G}{G}");
		this.expansionSetCode = "SOM";
		this.color.setGreen(true);
		this.getSpellAbility().addEffect(new GenesisWaveEffect());
	}

	public GenesisWave(final GenesisWave card) {
		super(card);
	}

	@Override
	public GenesisWave copy() {
		return new GenesisWave(this);
	}

}

class GenesisWaveEffect extends OneShotEffect<GenesisWaveEffect> {

	public GenesisWaveEffect() {
		super(Outcome.PutCardInPlay);
		staticText = "Reveal the top X cards of your library. You may put any number of permanent cards with converted mana cost X or less from among them onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield into your graveyard";
	}

	public GenesisWaveEffect(final GenesisWaveEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		Cards cards = new CardsImpl(Zone.PICK);
		int count = source.getManaCostsToPay().getVariableCosts().get(0).getAmount();
		for (int i = 0; i < count; i++) {
			Card card = player.getLibrary().removeFromTop(game);
			cards.add(card);
			game.setZone(card.getId(), Zone.PICK);
		}
		FilterCard filter = new FilterCard("card with converted mana cost " + count + " or less to put onto the battlefield");
		filter.setConvertedManaCost(count + 1);
		filter.setConvertedManaCostComparison(ComparisonType.LessThan);
		TargetCard target1 = new TargetCard(Zone.PICK, filter);
		while (cards.size() > 0 && player.choose(Outcome.PutCardInPlay, cards, target1, game)) {
			Card card = cards.get(target1.getFirstTarget(), game);
			if (card != null) {
				cards.remove(card);
				card.putOntoBattlefield(game, Zone.HAND, source.getSourceId(), source.getControllerId());
			}
			target1.clearChosen();
		}
		while (cards.size() > 0) {
			Card card = cards.get(cards.iterator().next(), game);
			cards.remove(card);
			card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
		}
		return true;
	}

	@Override
	public GenesisWaveEffect copy() {
		return new GenesisWaveEffect(this);
	}

}

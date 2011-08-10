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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continious.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PraetorsCounsel extends CardImpl<PraetorsCounsel> {

	public PraetorsCounsel(UUID ownerId) {
		super(ownerId, 88, "Praetor's Counsel", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{5}{G}{G}{G}");
		this.expansionSetCode = "MBS";
		this.color.setGreen(true);
		this.getSpellAbility().addEffect(new PraetorsCounselEffect());
		this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
		this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.EndOfGame, false));
	}

	public PraetorsCounsel(final PraetorsCounsel card) {
		super(card);
	}

	@Override
	public PraetorsCounsel copy() {
		return new PraetorsCounsel(this);
	}

}

class PraetorsCounselEffect extends OneShotEffect<PraetorsCounselEffect> {

	public PraetorsCounselEffect() {
		super(Outcome.DrawCard);
		this.staticText = "Return all cards from your graveyard to your hand";
	}

	public PraetorsCounselEffect(final PraetorsCounselEffect effect) {
		super(effect);
	}

	@Override
	public PraetorsCounselEffect copy() {
		return new PraetorsCounselEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		for (Card card: player.getGraveyard().getCards(game)) {
			player.putInHand(card, game);
		}
		player.getGraveyard().clear();
		return false;
	}

}

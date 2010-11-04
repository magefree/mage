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

package mage.sets.magic2010;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HauntingEchoes extends CardImpl<HauntingEchoes> {

	public HauntingEchoes(UUID ownerId) {
		super(ownerId, 98, "Haunting Echoes", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
		this.expansionSetCode = "M10";
		this.color.setBlack(true);
		this.getSpellAbility().addTarget(new TargetPlayer());
		this.getSpellAbility().addEffect(new HauntingEchoesEffect());
	}

	public HauntingEchoes(final HauntingEchoes card) {
		super(card);
	}

	@Override
	public HauntingEchoes copy() {
		return new HauntingEchoes(this);
	}

	@Override
	public String getArt() {
		return "122152_typ_reg_sty_010.jpg";
	}
}

class HauntingEchoesEffect extends OneShotEffect<HauntingEchoesEffect> {

	private static FilterBasicLandCard filter = new FilterBasicLandCard();

	public HauntingEchoesEffect() {
		super(Outcome.Detriment);
	}

	public HauntingEchoesEffect(final HauntingEchoesEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		Set<UUID> remove = new HashSet<UUID>();
		for (Iterator<UUID> i = player.getGraveyard().iterator(); i.hasNext();) {
			Card card = game.getCard(i.next());
			if (!filter.match(card)) {
				i.remove();
				game.getExile().add(card);
				for (UUID cardId: player.getLibrary().getCardList()) {
					if (game.getCard(cardId).getName().equals(card.getName())) {
						remove.add(cardId);
					}
				}
			}
		}
		for (UUID cardId: remove) {
			game.getExile().add(player.getLibrary().remove(cardId, game));
		}
		game.getPlayer(source.getControllerId()).lookAtCards(new CardsImpl(Zone.PICK, player.getLibrary().getCards(game)), game);
		player.shuffleLibrary(game);
		return true;
	}

	@Override
	public HauntingEchoesEffect copy() {
		return new HauntingEchoesEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "Exile all cards from target player's graveyard other than basic land cards. For each card exiled this way, search that player's library for all cards with the same name as that card and exile them. Then that player shuffles his or her library";
	}
}
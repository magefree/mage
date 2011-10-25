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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.players.Player;
import mage.sets.Sets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ConundrumSphinx extends CardImpl<ConundrumSphinx> {

	public ConundrumSphinx(UUID ownerId) {
		super(ownerId, 51, "Conundrum Sphinx", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
		this.expansionSetCode = "M11";
		this.subtype.add("Sphinx");
		this.color.setBlue(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(4);

		this.addAbility(FlyingAbility.getInstance());
		this.addAbility(new AttacksTriggeredAbility(new ConundrumSphinxEffect(), false));
	}

	public ConundrumSphinx(final ConundrumSphinx card) {
		super(card);
	}

	@Override
	public ConundrumSphinx copy() {
		return new ConundrumSphinx(this);
	}

}

class ConundrumSphinxEffect extends OneShotEffect<ConundrumSphinxEffect> {

	public ConundrumSphinxEffect() {
		super(Outcome.DrawCard);
		staticText = "each player names a card. Then each player reveals the top card of his or her library. If the card a player revealed is the card he or she named, that player puts it into his or her hand. If it's not, that player puts it on the bottom of his or her library";
	}

	public ConundrumSphinxEffect(final ConundrumSphinxEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Choice cardChoice = new ChoiceImpl();
		cardChoice.setChoices(Sets.getCardNames());
		for (Player player: game.getPlayers().values()) {
			if(player.getLibrary().size() > 0){
				cardChoice.clearChoice();
				while (!player.choose(Outcome.DrawCard, cardChoice, game)) {
                    game.debugMessage("player canceled choosing name. retrying.");
                }
                String cardName = cardChoice.getChoice();
                game.informPlayers("Conundrum Sphinx, player: " + player.getName() + ", named card: [" + cardName + "]");
				Card card = player.getLibrary().removeFromTop(game);
				Cards cards  = new CardsImpl();
				cards.add(card);
				player.revealCards("Conundrum Sphinx", cards, game);
				if (card.getName().equals(cardName)) {
					card.moveToZone(Zone.HAND, source.getId(), game, true);
				}
				else {
					card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
				}
			}
		}
		return true;
	}

	@Override
	public ConundrumSphinxEffect copy() {
		return new ConundrumSphinxEffect(this);
	}

}
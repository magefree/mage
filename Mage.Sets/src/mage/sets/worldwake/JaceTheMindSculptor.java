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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.sets.Worldwake;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class JaceTheMindSculptor extends CardImpl {


	public JaceTheMindSculptor(UUID ownerId) {
		super(ownerId, "Jace, the Mind Sculptor", new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
		this.expansionSetId = Worldwake.getInstance().getId();
		this.subtype.add("Jace");
		this.color.setBlue(true);
		this.art = "126493_typ_reg_sty_010.jpg";
		this.loyalty = new MageInt(3);

		LoyaltyAbility ability1 = new LoyaltyAbility(new JaceTheMindSculptorEffect1(), 2);
		ability1.addTarget(new TargetPlayer());
		this.addAbility(ability1);

		LoyaltyAbility ability2 = new LoyaltyAbility(new JaceTheMindSculptorEffect2(), 0);
		this.addAbility(ability2);

		LoyaltyAbility ability3 = new LoyaltyAbility(new ReturnToHandTargetEffect(), -1);
		ability3.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability3);

		LoyaltyAbility ability4 = new LoyaltyAbility(new JaceTheMindSculptorEffect3(), -12);
		ability4.addTarget(new TargetPlayer());
		this.addAbility(ability4);

	}

}

class JaceTheMindSculptorEffect1 extends OneShotEffect {

	public JaceTheMindSculptorEffect1() {
		super(Outcome.Detriment);
	}

	@Override
	public boolean apply(Game game) {
		Player controller = game.getPlayer(this.source.getControllerId());
		Player player = game.getPlayer(this.source.getFirstTarget());
		if (controller != null && player != null) {
			Cards cards = new CardsImpl(Zone.REVEALED);
			cards.add(player.getLibrary().getFromTop(game));
			controller.lookAtCards(cards, game);
			if (controller.chooseUse(outcome, "Do you wish to put card on the bottom of player's library?", game)) {
				Card card = player.getLibrary().removeFromTop(game);
				if (card != null)
					player.getLibrary().putOnBottom(card, game);
			}
			return true;
		}
		return false;
	}

	@Override
	public String getText() {
		return "Look at the top card of target player's library. You may put that card on the bottom of that player's library";
	}

}

class JaceTheMindSculptorEffect2 extends OneShotEffect {

	public JaceTheMindSculptorEffect2() {
		super(Outcome.DrawCard);
	}

	@Override
	public boolean apply(Game game) {
		Player player = game.getPlayer(this.source.getControllerId());
		if (player != null) {
			player.drawCards(3, game);
			putOnLibrary(player, game);
			putOnLibrary(player, game);
			return true;
		}
		return false;
	}

	private boolean putOnLibrary(Player player, Game game) {
		TargetCardInHand target = new TargetCardInHand();
		target.setRequired(true);
		target.setAbility(source);
		player.chooseTarget(Outcome.ReturnToHand, target, game);
		Card card = player.getHand().get(target.getFirstTarget());
		if (card != null) {
			player.getHand().remove(card);
			player.getLibrary().putOnTop(card, game);
		}
		return true;
	}

	@Override
	public String getText() {
		return "Draw three cards, then put two cards from your hand on top of your library in any order";
	}

}

class JaceTheMindSculptorEffect3 extends OneShotEffect {

	public JaceTheMindSculptorEffect3() {
		super(Outcome.DrawCard);
	}

	@Override
	public boolean apply(Game game) {
		Player player = game.getPlayer(this.source.getFirstTarget());
		ExileZone exile = game.getExile().getPermanentExile();
		if (player != null) {
			while (true) {
				if (player.getLibrary().getFromTop(game) == null)
					break;
				Card card = player.getLibrary().removeFromTop(game);
				exile.add(card);
			}
			player.getLibrary().addAll(player.getHand());
			player.getLibrary().shuffle();
			player.getHand().clear();
			return true;
		}
		return false;
	}

	@Override
	public String getText() {
		return "Exile all cards from target player's library, then that player shuffles his or her hand into his or her library";
	}

}
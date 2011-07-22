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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MerfolkSpy extends CardImpl<MerfolkSpy> {

	public MerfolkSpy(UUID ownerId) {
		super(ownerId, 66, "Merfolk Spy", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}");
		this.expansionSetCode = "M11";
		this.subtype.add("Merfolk");
		this.subtype.add("Rogue");
		this.color.setBlue(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

		this.addAbility(new IslandwalkAbility());
		this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MerfolkSpyEffect(), false));
	}

	public MerfolkSpy(final MerfolkSpy card) {
		super(card);
	}

	@Override
	public MerfolkSpy copy() {
		return new MerfolkSpy(this);
	}

}

class MerfolkSpyEffect extends OneShotEffect<MerfolkSpyEffect> {

	public MerfolkSpyEffect() {
		super(Outcome.Detriment);
		staticText = "that player reveals a card at random from his or her hand";
	}

	public MerfolkSpyEffect(final MerfolkSpyEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null && player.getHand().size() > 0) {
			Cards revealed = new CardsImpl();
			revealed.add(player.getHand().getRandom(game));
			player.revealCards("Merfolk Spy", revealed, game);
			return true;
		}
		return false;
	}

	@Override
	public MerfolkSpyEffect copy() {
		return new MerfolkSpyEffect(this);
	}

}
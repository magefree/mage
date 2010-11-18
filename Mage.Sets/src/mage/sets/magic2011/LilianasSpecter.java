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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LilianasSpecter extends CardImpl<LilianasSpecter> {

	public LilianasSpecter(UUID ownerId) {
		super(ownerId, 104, "Liliana's Specter", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
		this.expansionSetCode = "M11";
		this.subtype.add("Specter");
		this.color.setBlack(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(1);

		this.addAbility(FlyingAbility.getInstance());
		this.addAbility(new EntersBattlefieldTriggeredAbility(new LilianasSpecterEffect(), false));
	}

	public LilianasSpecter(final LilianasSpecter card) {
		super(card);
	}

	@Override
	public LilianasSpecter copy() {
		return new LilianasSpecter(this);
	}

	@Override
	public String getArt() {
		return "129091_typ_reg_sty_010.jpg";
	}

}

class LilianasSpecterEffect extends OneShotEffect<LilianasSpecterEffect> {

	public LilianasSpecterEffect() {
		super(Outcome.Discard);
	}

	public LilianasSpecterEffect(final LilianasSpecterEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		for (UUID playerId: game.getOpponents(source.getControllerId())) {
			Player player = game.getPlayer(playerId);
			player.discard(1, source, game);
		}
		return true;
	}

	@Override
	public LilianasSpecterEffect copy() {
		return new LilianasSpecterEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "each opponent discards a card";
	}
}
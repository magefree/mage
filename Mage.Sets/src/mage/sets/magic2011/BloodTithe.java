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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class BloodTithe extends CardImpl<BloodTithe> {

	public BloodTithe(UUID ownerId) {
		super(ownerId, 84, "Blood Tithe", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
		this.expansionSetCode = "M11";
		this.getColor().setBlack(true);

		this.getSpellAbility().addEffect(new BloodTitheEffect());
	}

	public BloodTithe(final BloodTithe card) {
		super(card);
	}

	@Override
	public BloodTithe copy() {
		return new BloodTithe(this);
	}

	@Override
	public String getArt() {
		return "129141_typ_reg_sty_010.jpg";
	}

}

class BloodTitheEffect extends OneShotEffect<BloodTitheEffect> {

	public BloodTitheEffect() {
		super(Outcome.Damage);
	}

	public BloodTitheEffect(final BloodTitheEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int damage = 0;
		for (UUID opponentId: game.getOpponents(source.getControllerId())) {
			damage += game.getPlayer(opponentId).damage(3, source.getSourceId(), game, false, true);
		}
		game.getPlayer(source.getControllerId()).gainLife(damage, game);
		return true;
	}

	@Override
	public BloodTitheEffect copy() {
		return new BloodTitheEffect(this);
	}

}
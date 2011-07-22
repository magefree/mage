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
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ChandrasOutrage extends CardImpl<ChandrasOutrage> {

	public ChandrasOutrage(UUID ownerId) {
		super(ownerId, 128, "Chandra's Outrage", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");
		this.expansionSetCode = "M11";
		this.color.setRed(true);
		this.getSpellAbility().addEffect(new ChandrasOutrageEffect());
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
	}

	public ChandrasOutrage(final ChandrasOutrage card) {
		super(card);
	}

	@Override
	public ChandrasOutrage copy() {
		return new ChandrasOutrage(this);
	}

}

class ChandrasOutrageEffect extends OneShotEffect<ChandrasOutrageEffect> {

	public ChandrasOutrageEffect() {
		super(Outcome.Damage);
		staticText = "Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller";
	}

	public ChandrasOutrageEffect(final ChandrasOutrageEffect effect) {
		super(effect);
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			Player player = game.getPlayer(permanent.getControllerId());
			if (player != null) {
				permanent.damage(4, source.getSourceId(), game, true, false);
				player.damage(2, source.getSourceId(), game, false, true);
				return true;
			}
		}
		return false;
	}

	@Override
	public ChandrasOutrageEffect copy() {
		return new ChandrasOutrageEffect(this);
	}

}
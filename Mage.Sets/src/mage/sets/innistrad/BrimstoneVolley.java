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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.Watcher;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class BrimstoneVolley extends CardImpl<BrimstoneVolley> {

	public BrimstoneVolley(UUID ownerId) {
		super(ownerId, 132, "Brimstone Volley", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
		this.expansionSetCode = "ISD";

		this.color.setRed(true);

		// Brimstone Volley deals 3 damage to target creature or player.
		// Morbid - Brimstone Volley deals 5 damage to that creature or player instead if a creature died this turn.
		this.getSpellAbility().addEffect(new BrimstoneVolleyEffect());
		this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
	}

	public BrimstoneVolley(final BrimstoneVolley card) {
		super(card);
	}

	@Override
	public BrimstoneVolley copy() {
		return new BrimstoneVolley(this);
	}
}

class BrimstoneVolleyEffect extends OneShotEffect<BrimstoneVolleyEffect> {

	public BrimstoneVolleyEffect() {
		super(Constants.Outcome.Damage);
		staticText = "{this} deals 3 damage to target creature or player.\n Morbid - {this} deals 5 damage to that creature or player instead if a creature died this turn";
	}

	public BrimstoneVolleyEffect(final BrimstoneVolleyEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int damage = 3;
		Watcher watcher = game.getState().getWatchers().get("Morbid");
		if (watcher.conditionMet()) {
		  	damage = 5;
		}
		Permanent permanent = game.getPermanent(targetPointer.getFirst(source));
		if (permanent != null) {
			permanent.damage(damage, source.getSourceId(), game, true, false);
			return true;
		}
		Player player = game.getPlayer(targetPointer.getFirst(source));
		if (player != null) {
			player.damage(damage, source.getSourceId(), game, false, true);
			return true;
		}
		return false;
	}

	@Override
	public BrimstoneVolleyEffect copy() {
		return new BrimstoneVolleyEffect(this);
	}

}

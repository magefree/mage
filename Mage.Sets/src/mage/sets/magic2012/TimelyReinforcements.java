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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class TimelyReinforcements extends CardImpl<TimelyReinforcements> {

	public TimelyReinforcements(UUID ownerId) {
		super(ownerId, 40, "Timely Reinforcements", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
		this.expansionSetCode = "M12";
		this.color.setWhite(true);

		// If you have less life than an opponent, you gain 6 life. If you control fewer creatures than an opponent, put three 1/1 white Soldier creature tokens onto the battlefield.
		this.getSpellAbility().addEffect(new TimelyReinforcementsEffect());
	}

	public TimelyReinforcements(final TimelyReinforcements card) {
		super(card);
	}

	@Override
	public TimelyReinforcements copy() {
		return new TimelyReinforcements(this);
	}
}

class TimelyReinforcementsEffect extends OneShotEffect<TimelyReinforcementsEffect> {

	public TimelyReinforcementsEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "If you have less life than an opponent, you gain 6 life. If you control fewer creatures than an opponent, put three 1/1 white Soldier creature tokens onto the battlefield";
	}

	public TimelyReinforcementsEffect(TimelyReinforcementsEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
		if (controller != null) {
			boolean lessCreatures = false;
			boolean lessLife = false;
			FilterPermanent filter= new FilterCreaturePermanent();
			int count = game.getBattlefield().countAll(filter, controller.getId(), game);
			for (UUID uuid : game.getOpponents(controller.getId())) {
				Player opponent = game.getPlayer(uuid);
				if (opponent != null) {
					if (opponent.getLife() > controller.getLife()) {
						 lessLife = true;
					}
					if (game.getBattlefield().countAll(filter, uuid, game) > count) {
						lessCreatures = true;
					}
				}
				if ( lessLife && lessCreatures) { // no need to search further
					break;
				}
			}
			if (lessLife) {
				controller.gainLife(6, game);
			}
			if (lessCreatures) {
				Effect effect = new CreateTokenEffect(new SoldierToken(), 3);
				effect.apply(game, source);
			}
			return true;
		}
		return false;
	}

	@Override
	public TimelyReinforcementsEffect copy() {
		return new TimelyReinforcementsEffect(this);
	}
}

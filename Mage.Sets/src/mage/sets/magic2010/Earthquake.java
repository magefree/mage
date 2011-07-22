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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Earthquake extends CardImpl<Earthquake> {

	public Earthquake(UUID ownerId) {
		super(ownerId, 134, "Earthquake", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{R}");
		this.expansionSetCode = "M10";
		this.color.setRed(true);
		this.getSpellAbility().addEffect(new EarthquakeEffect());
	}

	public Earthquake(final Earthquake card) {
		super(card);
	}

	@Override
	public Earthquake copy() {
		return new Earthquake(this);
	}
}

class EarthquakeEffect extends OneShotEffect<EarthquakeEffect> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

	static {
		filter.getAbilities().add(FlyingAbility.getInstance());
		filter.setNotAbilities(true);
	}

	public EarthquakeEffect() {
		super(Outcome.Damage);
		staticText = "{this} deals X damage to each creature without flying and each player";
	}

	public EarthquakeEffect(final EarthquakeEffect effect) {
		super(effect);
	}

	@Override
	public EarthquakeEffect copy() {
		return new EarthquakeEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int amount = source.getManaCostsToPay().getVariableCosts().get(0).getAmount();

		for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
			permanent.damage(amount, source.getId(), game, true, false);
		}
		for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
			Player player = game.getPlayer(playerId);
			if (player != null)
				player.damage(amount, source.getSourceId(), game, false, true);
		}
		return true;
	}

}

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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.Magic2010;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Earthquake extends CardImpl {

	public Earthquake(UUID ownerId) {
		super(ownerId, "Earthquake", new CardType[]{CardType.SORCERY}, "{X}{R}");
		this.expansionSetId = Magic2010.getInstance().getId();
		this.color.setRed(true);
		this.art = "118685_typ_reg_sty_010.jpg";
		this.getSpellAbility().addEffect(new EarthquakeEffect());
	}
}

class EarthquakeEffect extends OneShotEffect {

	public EarthquakeEffect() {
		super(Outcome.Damage);
	}

	@Override
	public boolean apply(Game game) {
		int amount = this.source.getManaCosts().getVariableCosts().get(0).getValue();

		FilterCreaturePermanent filter = new FilterCreaturePermanent();
		filter.getAbilities().add(FlyingAbility.getInstance());
		filter.setNotAbilities(true);
		for (Permanent permanent: game.getBattlefield().getActivePermanents(filter, this.source.getControllerId(), game)) {
			permanent.damage(amount, source.getId(), game);
		}
		for (UUID playerId: game.getPlayer(this.source.getControllerId()).getInRange()) {
			Player player = game.getPlayer(playerId);
			if (player != null)
				player.damage(amount, source.getId(), game);
		}
		return true;
	}

	@Override
	public String getText() {
		return "Earthquake deals X damage to each creature without flying and each player";
	}
}

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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SorcerersStrongbox extends CardImpl<SorcerersStrongbox> {

	public SorcerersStrongbox(UUID ownerId) {
		super(ownerId, 213, "Sorcerer's Strongbox", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
		this.expansionSetCode = "M11";
		Costs costs = new CostsImpl();
		costs.add(new GenericManaCost(2));
		costs.add(new TapSourceCost());
		this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SorcerersStrongboxEffect(), costs));
	}

	public SorcerersStrongbox(final SorcerersStrongbox card) {
		super(card);
	}

	@Override
	public SorcerersStrongbox copy() {
		return new SorcerersStrongbox(this);
	}

}

class SorcerersStrongboxEffect extends OneShotEffect<SorcerersStrongboxEffect> {

	public SorcerersStrongboxEffect() {
		super(Outcome.DrawCard);
	}

	public SorcerersStrongboxEffect(final SorcerersStrongboxEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		if (player != null) {
			if (player.flipCoin()) {
				Permanent perm = game.getPermanent(source.getSourceId());
				if (perm != null) {
					perm.sacrifice(source.getId(), game);
				}
				player.drawCards(3, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public SorcerersStrongboxEffect copy() {
		return new SorcerersStrongboxEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "Flip a coin. If you win the flip, sacrifice Sorcerer's Strongbox and draw three cards";
	}
}
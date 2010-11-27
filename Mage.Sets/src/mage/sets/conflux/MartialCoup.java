/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MartialCoup extends CardImpl<MartialCoup> {

	public MartialCoup(UUID ownerId) {
		super(ownerId, 11, "Martial Coup", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");
		this.expansionSetCode = "CON";
		this.color.setWhite(true);
		this.getSpellAbility().addEffect(new MartialCoupEffect());
	}

	public MartialCoup(final MartialCoup card) {
		super(card);
	}

	@Override
	public MartialCoup copy() {
		return new MartialCoup(this);
	}

	@Override
	public String getArt() {
		return "118685_typ_reg_sty_010.jpg";
	}
}

class MartialCoupEffect extends OneShotEffect<MartialCoupEffect> {

	private static SoldierToken token = new SoldierToken();

	public MartialCoupEffect() {
		super(Outcome.PutCreatureInPlay);
	}

	public MartialCoupEffect(final MartialCoupEffect effect) {
		super(effect);
	}

	@Override
	public MartialCoupEffect copy() {
		return new MartialCoupEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int amount = 0;
		if (source.getManaCosts().getVariableCosts().size() > 0) {
			amount = source.getManaCosts().getVariableCosts().get(0).getAmount();
		}

		if (amount > 4) {
			for (Permanent permanent: game.getBattlefield().getActivePermanents(FilterCreaturePermanent.getDefault(), source.getControllerId(), game)) {
				permanent.destroy(source.getSourceId(), game, false);
			}
		}
		for (int i = 0; i < amount; i++) {
			token.putOntoBattlefield(game, source.getId(), source.getControllerId());
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		return "Put X 1/1 white Soldier creature tokens onto the battlefield. If X is 5 or more, destroy all other creatures";
	}

}

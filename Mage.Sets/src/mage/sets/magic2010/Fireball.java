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
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Fireball extends CardImpl<Fireball> {

	public Fireball(UUID ownerId) {
		super(ownerId, 136, "Fireball", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}");
		this.expansionSetCode = "M10";
		this.color.setRed(true);
		this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(0, Integer.MAX_VALUE));
		this.getSpellAbility().addEffect(new FireballEffect());
	}

	@Override
	public void adjustCosts(Ability ability, Game game) {
		int numTargets = ability.getTargets().get(0).getTargets().size();
		if (numTargets > 1) {
			ability.getManaCostsToPay().add(new GenericManaCost(numTargets - 1));
		}
	}

	public Fireball(final Fireball card) {
		super(card);
	}

	@Override
	public Fireball copy() {
		return new Fireball(this);
	}
}

class FireballEffect extends OneShotEffect<FireballEffect> {

	public FireballEffect() {
		super(Outcome.Damage);
		staticText = "{this} deals X damage divided evenly, rounded down, among any number of target creatures and/or players.\n {this} costs {1} more to cast for each target beyond the first";
	}

	public FireballEffect(final FireballEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int numTargets = targetPointer.getTargets(source).size();
		int damage = source.getManaCostsToPay().getX();
		if (numTargets > 0) {
			int damagePer = damage/numTargets;
			if (damagePer > 0) {
				for (UUID targetId: targetPointer.getTargets(source)) {
					Permanent permanent = game.getPermanent(targetId);
					if (permanent != null) {
						permanent.damage(damagePer, source.getSourceId(), game, true, false);
					}
					else {
						Player player = game.getPlayer(targetId);
						if (player != null) {
							player.damage(damagePer, source.getSourceId(), game, false, true);
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public FireballEffect copy() {
		return new FireballEffect(this);
	}

}
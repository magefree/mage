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
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Corrupt extends CardImpl<Corrupt> {

	public Corrupt(UUID ownerId) {
		super(ownerId, 89, "Corrupt", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{5}{B}");
		this.expansionSetCode = "M11";
		this.color.setBlack(true);
		this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
		this.getSpellAbility().addEffect(new CorruptEffect());
	}

	public Corrupt(final Corrupt card) {
		super(card);
	}

	@Override
	public Corrupt copy() {
		return new Corrupt(this);
	}

}

class CorruptEffect extends OneShotEffect<CorruptEffect> {

	private static final FilterLandPermanent filter = new FilterLandPermanent("Swamps");

	static {
		filter.getSubtype().add("Swamp");
		filter.setScopeSubtype(ComparisonScope.Any);
		filter.setTargetController(TargetController.YOU);
	}

	public CorruptEffect() {
		super(Outcome.Damage);
		staticText = "Corrupt deals damage equal to the number of Swamps you control to target creature or player. You gain life equal to the damage dealt this way";
	}

	public CorruptEffect(final CorruptEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int amount = game.getBattlefield().count(filter, source.getControllerId(), game);
		if (amount > 0) {
			int damageDealt = amount;
			Permanent permanent = game.getPermanent(source.getFirstTarget());
			if (permanent != null) {
				damageDealt = permanent.damage(amount, source.getSourceId(), game, true, false);
			}
			else {
				Player player = game.getPlayer(source.getFirstTarget());
				if (player != null) {
					damageDealt = player.damage(amount, source.getSourceId(), game, false, true);
				}
				else
					return false;
			}
			Player you = game.getPlayer(source.getControllerId());
			if (you != null) {
				you.gainLife(damageDealt, game);
				return true;
			}
		}
		return false;
	}

	@Override
	public CorruptEffect copy() {
		return new CorruptEffect(this);
	}

}
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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ConsumeSpirit extends CardImpl<ConsumeSpirit> {

	public static final FilterMana filterBlack = new FilterMana();

	static {
		filterBlack.setBlack(true);
	}

	public ConsumeSpirit(UUID ownerId) {
		super(ownerId, 89, "Consume Spirit", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");
		this.expansionSetCode = "M10";

		this.color.setBlack(true);

		// Spend only black mana on X.
		// Consume Spirit deals X damage to target creature or player and you gain X life.
		this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
		this.getSpellAbility().addEffect(new ConsumeSpiritEffect());
		this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0).setFilter(filterBlack);
	}

	public ConsumeSpirit(final ConsumeSpirit card) {
		super(card);
	}

	@Override
	public ConsumeSpirit copy() {
		return new ConsumeSpirit(this);
	}
}

class ConsumeSpiritEffect extends OneShotEffect<ConsumeSpiritEffect> {

	public ConsumeSpiritEffect() {
		super(Constants.Outcome.Damage);
		staticText = "Consume Spirit deals X damage to target creature or player and you gain X life.Spend only black mana on X";
	}

	public ConsumeSpiritEffect(final ConsumeSpiritEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int damage = source.getManaCostsToPay().getX();
		if (damage > 0) {
			Permanent permanent = game.getPermanent(getTargetPointer().getFirst(source));
			if (permanent != null) {
				permanent.damage(damage, source.getSourceId(), game, true, false);
			} else {
				Player player = game.getPlayer(getTargetPointer().getFirst(source));
				if (player != null) {
					player.damage(damage, source.getSourceId(), game, false, true);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public ConsumeSpiritEffect copy() {
		return new ConsumeSpiritEffect(this);
	}

}

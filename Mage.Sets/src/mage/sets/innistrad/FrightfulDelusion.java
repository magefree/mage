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

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 * 
 * @author Rafbill
 */
public class FrightfulDelusion extends CardImpl<FrightfulDelusion> {

	public FrightfulDelusion(UUID ownerId) {
        super(ownerId, 57, "Frightful Delusion", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "ISD";

        this.color.setBlue(true);

        // Counter target spell unless its controller pays {1}. That player discards a card.
        this.getSpellAbility().addTarget(new TargetSpell());
		this.getSpellAbility().addEffect(new FrightfulDelusionEffect());
    }

	public FrightfulDelusion(final FrightfulDelusion card) {
		super(card);
	}

	@Override
	public FrightfulDelusion copy() {
		return new FrightfulDelusion(this);
	}
}

class FrightfulDelusionEffect extends OneShotEffect<FrightfulDelusionEffect> {

	public FrightfulDelusionEffect() {
		super(Outcome.Detriment);
		this.staticText = "Counter target spell unless its controller pays {1}. That player discards a card.";
	}

	public FrightfulDelusionEffect(final FrightfulDelusionEffect effect) {
		super(effect);
	}

	@Override
	public FrightfulDelusionEffect copy() {
		return new FrightfulDelusionEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		StackObject spell = game.getStack().getStackObject(
				targetPointer.getFirst(source));
		Cost cost = new GenericManaCost(1);
		if (spell != null) {
			Player player = game.getPlayer(spell.getControllerId());
			if (player != null) {
				cost.clearPaid();
				if (!cost.pay(source, game, spell.getControllerId(),
						spell.getControllerId(), false)) {
					game.getPlayer(spell.getControllerId()).discard(
							1, source, game);
					return game.getStack().counter(source.getFirstTarget(),
							source.getSourceId(), game);
				}
			}
		}
		return false;
	}

}

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
package mage.sets.scarsofmirrodin;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class EchoCirclet extends CardImpl<EchoCirclet> {

	public EchoCirclet(UUID ownerId) {
		super(ownerId, 153, "Echo Circlet", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Equipment");

		this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(1)));
		// Equipped creature can block an additional creature.
		this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EchoCircletEffect()));
	}

	public EchoCirclet(final EchoCirclet card) {
		super(card);
	}

	@Override
	public EchoCirclet copy() {
		return new EchoCirclet(this);
	}
}

class EchoCircletEffect extends ContinuousEffectImpl<EchoCircletEffect> {

	public EchoCircletEffect() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
		staticText = "Equipped creature can block an additional creature";
	}

	public EchoCircletEffect(final EchoCircletEffect effect) {
		super(effect);
	}

	@Override
	public EchoCircletEffect copy() {
		return new EchoCircletEffect(this);
	}

	@Override
	public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
		Permanent perm = game.getPermanent(source.getSourceId());
		if (perm != null && perm.getAttachedTo() != null) {
			Permanent equipped = game.getPermanent(perm.getAttachedTo());
			if (equipped != null) {
				switch (layer) {
					case RulesEffects:
						// maxBlocks = 0 equals to "can block any number of creatures"
						if (equipped.getMaxBlocks() > 0) {
							equipped.setMaxBlocks(equipped.getMaxBlocks() + 1);
						}
						break;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean hasLayer(Constants.Layer layer) {
		return layer == Constants.Layer.RulesEffects;
	}

}

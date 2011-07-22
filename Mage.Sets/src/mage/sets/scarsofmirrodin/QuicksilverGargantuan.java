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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ayratn
 */
public class QuicksilverGargantuan extends CardImpl<QuicksilverGargantuan> {

	private static final String text = "You may have Quicksilver Gargantuan enter the battlefield as a copy of any creature on the battlefield, except it's still 7/7";

	public QuicksilverGargantuan(UUID ownerId) {
		super(ownerId, 39, "Quicksilver Gargantuan", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Shapeshifter");
		this.color.setBlue(true);
		this.power = new MageInt(7);
		this.toughness = new MageInt(7);

		Ability ability = new EntersBattlefieldAbility(new QuicksilverGargantuanCopyEffect(), text);
		ability.addTarget(new TargetCreaturePermanent());
		this.addAbility(ability);
	}

	public QuicksilverGargantuan(final QuicksilverGargantuan card) {
		super(card);
	}

	@Override
	public QuicksilverGargantuan copy() {
		return new QuicksilverGargantuan(this);
	}

	private class QuicksilverGargantuanCopyEffect extends ContinuousEffectImpl<QuicksilverGargantuanCopyEffect> {

		public QuicksilverGargantuanCopyEffect() {
			super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.BecomeCreature);
			staticText =  "You may have Quicksilver Gargantuan enter the battlefield as a copy of any creature on the battlefield, except it's still 7/7";
		}

		public QuicksilverGargantuanCopyEffect(final QuicksilverGargantuanCopyEffect effect) {
			super(effect);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			Card card = game.getCard(source.getFirstTarget());
			Permanent permanent = game.getPermanent(source.getSourceId());
			permanent.setName(card.getName());
			permanent.getColor().setColor(card.getColor());
			permanent.getManaCost().clear();
			permanent.getManaCost().add(card.getManaCost());
			permanent.getCardType().clear();
			for (CardType type : card.getCardType()) {
				permanent.getCardType().add(type);
			}
			permanent.getSubtype().clear();
			for (String type : card.getSubtype()) {
				permanent.getSubtype().add(type);
			}
			permanent.getSupertype().clear();
			for (String type : card.getSupertype()) {
				permanent.getSupertype().add(type);
			}
			permanent.setExpansionSetCode(card.getExpansionSetCode());
			permanent.getAbilities().clear();
			for (Ability ability : card.getAbilities()) {
				permanent.addAbility(ability);
			}

			return true;
		}

		@Override
		public QuicksilverGargantuanCopyEffect copy() {
			return new QuicksilverGargantuanCopyEffect(this);
		}

	}

}

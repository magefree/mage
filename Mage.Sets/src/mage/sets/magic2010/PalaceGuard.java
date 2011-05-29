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
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PalaceGuard extends CardImpl<PalaceGuard> {

	public PalaceGuard(UUID ownerId) {
		super(ownerId, 23, "Palace Guard", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
		this.expansionSetCode = "M10";
		this.subtype.add("Human");
		this.subtype.add("Soldier");
		this.color.setWhite(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(4);

		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PalaceGuardEffect()));
	}

	public PalaceGuard(final PalaceGuard card) {
		super(card);
	}

	@Override
	public PalaceGuard copy() {
		return new PalaceGuard(this);
	}

	class PalaceGuardEffect extends ContinuousEffectImpl<PalaceGuardEffect> {

		public PalaceGuardEffect() {
			super(Duration.WhileOnBattlefield, Outcome.Benefit);
		}

		public PalaceGuardEffect(final PalaceGuardEffect effect) {
			super(effect);
		}

		@Override
		public PalaceGuardEffect copy() {
			return new PalaceGuardEffect(this);
		}

		@Override
		public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
			Permanent perm = game.getPermanent(source.getSourceId());
			if (perm != null) {
				switch (layer) {
					case RulesEffects:
						perm.setMaxBlocks(0);
						break;
				}
				return true;
			}
			return false;
		}

		@Override
		public boolean apply(Game game, Ability source) {
			return false;
		}

		@Override
		public boolean hasLayer(Layer layer) {
			return layer == Layer.RulesEffects;
		}

		@Override
		public String getText(Ability source) {
			return "Palace Guard can block any number of creatures";
		}
	}
}


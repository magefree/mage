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
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continious.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class VolitionReins extends CardImpl<VolitionReins> {

	public VolitionReins(UUID ownerId) {
		super(ownerId, 53, "Volition Reins", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}{U}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Aura");
		this.color.setBlue(true);

		TargetPermanent auraTarget = new TargetPermanent();
		this.getSpellAbility().addTarget(auraTarget);
		this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
		Ability ability = new EnchantAbility(auraTarget.getTargetName());
		this.addAbility(ability);
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));
		this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapVolitionReinsEffect()));
	}

	public VolitionReins(final VolitionReins card) {
		super(card);
	}

	@Override
	public VolitionReins copy() {
		return new VolitionReins(this);
	}

	public class UntapVolitionReinsEffect extends OneShotEffect<UntapVolitionReinsEffect> {

		public UntapVolitionReinsEffect() {
			super(Constants.Outcome.Untap);
			staticText = "When {this} enters the battlefield, if enchanted permanent is tapped, untap it.";
		}

		public UntapVolitionReinsEffect(final UntapVolitionReinsEffect effect) {
			super(effect);
		}

		@Override
		public UntapVolitionReinsEffect copy() {
			return new UntapVolitionReinsEffect(this);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			Permanent enchantment = game.getPermanent(source.getSourceId());
			if (enchantment != null && enchantment.getAttachedTo() != null) {
				Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
				if (permanent != null && permanent.isTapped()) {
					permanent.untap(game);
					return true;
				}
			}
			return false;
		}

	}

}

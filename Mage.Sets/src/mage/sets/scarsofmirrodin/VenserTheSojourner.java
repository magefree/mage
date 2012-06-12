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
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public class VenserTheSojourner extends CardImpl<VenserTheSojourner> {

	public VenserTheSojourner(UUID ownerId) {
		super(ownerId, 135, "Venser, the Sojourner", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{U}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Venser");
		this.color.setWhite(true);
		this.color.setBlue(true);
		this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), null));

		// +2: Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step.
		LoyaltyAbility ability1 = new LoyaltyAbility(new VenserTheSojournerEffect(), 2);
		ability1.addTarget(new TargetControlledPermanent());
		this.addAbility(ability1);

		//TODO: Venser's second ability doesn't lock in what it applies to. That's because the effect states a true thing about creatures,
		// but doesn't actually change the characteristics of those creatures. As a result, all creatures are unblockable that turn, including creatures you don't control, creatures that weren't on the battlefield at the time the ability resolved, and creatures that have lost all abilities.
		// -1: Creatures are unblockable this turn.
		this.addAbility(new LoyaltyAbility(new GainAbilityAllEffect(UnblockableAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterCreaturePermanent()), -1));

		// -8: You get an emblem with "Whenever you cast a spell, exile target permanent."
		LoyaltyAbility ability2 = new LoyaltyAbility(new GetEmblemEffect(new VenserTheSojournerEmblem()), -8);
		this.addAbility(ability2);
	}

	public VenserTheSojourner(final VenserTheSojourner card) {
		super(card);
	}

	@Override
	public VenserTheSojourner copy() {
		return new VenserTheSojourner(this);
	}

}

class VenserTheSojournerEffect extends OneShotEffect<VenserTheSojournerEffect> {

	private static final String effectText = "Exile target permanent you own. Return it to the battlefield under your control at the beginning of the next end step";

	VenserTheSojournerEffect() {
		super(Constants.Outcome.Benefit);
		staticText = effectText;
	}

	VenserTheSojournerEffect(VenserTheSojournerEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		if (getTargetPointer().getFirst(game, source) != null) {
			Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
			if (permanent != null) {
				if (permanent.moveToExile(source.getSourceId(), "Venser, the Sojourner", source.getSourceId(), game)) {
					//create delayed triggered ability
					AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
					delayedAbility.setSourceId(source.getSourceId());
					delayedAbility.setControllerId(source.getControllerId());
					game.addDelayedTriggeredAbility(delayedAbility);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public VenserTheSojournerEffect copy() {
		return new VenserTheSojournerEffect(this);
	}

}

class VenserTheSojournerEmblem extends Emblem {

	public VenserTheSojournerEmblem() {
		Ability ability = new SpellCastTriggeredAbility(new ExileTargetEffect(), false);
		Target target = new TargetPermanent();
		target.setRequired(true);
		ability.addTarget(target);
		this.getAbilities().add(ability);
	}
}

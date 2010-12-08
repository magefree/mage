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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AddPlusOneCountersSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.counters.PlusOneCounter;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ProteanHydra extends CardImpl<ProteanHydra> {

	public ProteanHydra(UUID ownerId) {
		super(ownerId, 200, "Protean Hydra", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{X}{G}");
		this.expansionSetCode = "M10";
		this.subtype.add("Hydra");
		this.color.setGreen(true);
		this.power = new MageInt(0);
		this.toughness = new MageInt(0);

		this.addAbility(new EntersBattlefieldAbility(new ProteanHydraEffect1(), "with X +1/+1 counters on it"));
		this.addAbility(new ProteanHydraAbility());
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ProteanHydraEffect2()));
	}

	public ProteanHydra(final ProteanHydra card) {
		super(card);
	}

	@Override
	public ProteanHydra copy() {
		return new ProteanHydra(this);
	}

	@Override
	public String getArt() {
		return "122168_typ_reg_sty_010.jpg";
	}

	class ProteanHydraEffect1 extends OneShotEffect<ProteanHydraEffect1> {

		public ProteanHydraEffect1() {
			super(Outcome.BoostCreature);
		}

		public ProteanHydraEffect1(final ProteanHydraEffect1 effect) {
			super(effect);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			Permanent permanent = game.getPermanent(source.getSourceId());
			if (permanent != null) {
				int amount = source.getManaCosts().getVariableCosts().get(0).getAmount();
				permanent.addCounters(new PlusOneCounter(amount));
			}
			return true;
		}

		@Override
		public ProteanHydraEffect1 copy() {
			return new ProteanHydraEffect1(this);
		}

		@Override
		public String getText(Ability source) {
			return "Protean Hydra enters the battlefield with X +1/+1 counters on it";
		}

	}

	class ProteanHydraEffect2 extends PreventionEffectImpl<ProteanHydraEffect2> {

		public ProteanHydraEffect2() {
			super(Duration.WhileOnBattlefield);
		}

		public ProteanHydraEffect2(final ProteanHydraEffect2 effect) {
			super(effect);
		}

		@Override
		public ProteanHydraEffect2 copy() {
			return new ProteanHydraEffect2(this);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			return true;
		}

		@Override
		public boolean replaceEvent(GameEvent event, Ability source, Game game) {
			boolean retValue = false;
			GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
			int damage = event.getAmount();
			if (!game.replaceEvent(preventEvent)) {
				event.setAmount(0);
				game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
				retValue = true;
			}
			Permanent permanent = game.getPermanent(source.getSourceId());
			if (permanent != null) {
				permanent.removeCounters("+1/+1", damage, game);
			}
			return retValue;
		}

		@Override
		public boolean applies(GameEvent event, Ability source, Game game) {
			if (super.applies(event, source, game)) {
				if (event.getTargetId().equals(source.getSourceId())) {
					return true;
				}
			}
			return false;
		}

		@Override
		public String getText(Ability source) {
			return "If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it";
		}

	}

	class ProteanHydraAbility extends TriggeredAbilityImpl<ProteanHydraAbility> {

		public ProteanHydraAbility() {
			super(Zone.BATTLEFIELD, new CreateDelayedTriggeredAbilityEffect(new ProteanHydraDelayedTriggeredAbility()), false);
		}

		public ProteanHydraAbility(final ProteanHydraAbility ability) {
			super(ability);
		}

		@Override
		public ProteanHydraAbility copy() {
			return new ProteanHydraAbility(this);
		}

		@Override
		public boolean checkTrigger(GameEvent event, Game game) {
			if (event.getType() == EventType.COUNTER_REMOVED && event.getData().equals("+1/+1") && event.getTargetId().equals(this.getSourceId())) {
				return true;
			}
			return false;
		}

		@Override
		public String getRule() {
			return "Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.";
		}

	}

	class ProteanHydraDelayedTriggeredAbility extends DelayedTriggeredAbility<ProteanHydraDelayedTriggeredAbility> {

		public ProteanHydraDelayedTriggeredAbility() {
			super(new AddPlusOneCountersSourceEffect(2));
		}

		public ProteanHydraDelayedTriggeredAbility(final ProteanHydraDelayedTriggeredAbility ability) {
			super(ability);
		}

		@Override
		public ProteanHydraDelayedTriggeredAbility copy() {
			return new ProteanHydraDelayedTriggeredAbility(this);
		}

		@Override
		public boolean checkTrigger(GameEvent event, Game game) {
			if (event.getType() == EventType.END_TURN_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
				return true;
			}
			return false;
		}

		@Override
		public String getRule() {
			return "Put two +1/+1 counters on {this} at the beginning of the next end step";
		}

	}
}

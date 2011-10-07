/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public class FlashbackAbility extends ActivatedAbilityImpl<FlashbackAbility> {

	public FlashbackAbility(ManaCosts costs, Constants.TimingRule timingRule) {
		super(Constants.Zone.GRAVEYARD, new FlashbackEffect(), costs);
		this.timing = timingRule;
		this.usesStack = false;
		this.addEffect(new CreateDelayedTriggeredAbilityEffect(new FlashbackTriggeredAbility()));
	}

	@Override
	public boolean activate(Game game, boolean noMana) {
		Card card = game.getCard(sourceId);
		if (card != null) {
			getEffects().get(0).setTargetPointer(new FixedTarget(card.getId()));
			return super.activate(game, noMana);
		}
		return false;
	}

	public FlashbackAbility(final FlashbackAbility ability) {
		super(ability);
	}

	@Override
	public FlashbackAbility copy() {
		return new FlashbackAbility(this);
	}

	@Override
	public String getRule() {
		StringBuilder sbRule = new StringBuilder("Flashback ");
		if (manaCosts.size() > 0) {
			sbRule.append(manaCosts.getText());
		}
		if (costs.size() > 0) {
			if (sbRule.length() > 0) {
				sbRule.append(",");
			}
			sbRule.append(costs.getText());
		}
		return sbRule.toString();
	}
}

class FlashbackEffect extends OneShotEffect<FlashbackEffect> {

	public FlashbackEffect() {
		super(Constants.Outcome.Benefit);
		staticText = "";
	}

	public FlashbackEffect(final FlashbackEffect effect) {
		super(effect);
	}

	@Override
	public FlashbackEffect copy() {
		return new FlashbackEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Card target = (Card) game.getObject(targetPointer.getFirst(source));
		if (target != null) {
			Player controller = game.getPlayer(target.getOwnerId());
			if (controller != null) {
                target.getSpellAbility().getManaCostsToPay().clear();
                for (Cost cost: source.getManaCostsToPay()) {
                    target.getSpellAbility().getManaCostsToPay().add((ManaCost) cost.copy());
                }
				return controller.cast(target.getSpellAbility(), game, true);
			}
		}
		return false;
	}
}

class FlashbackTriggeredAbility extends DelayedTriggeredAbility<FlashbackTriggeredAbility> {

	public FlashbackTriggeredAbility() {
		super(new ExileSourceEffect());
		usesStack = false;
	}

	public FlashbackTriggeredAbility(final FlashbackTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public FlashbackTriggeredAbility copy() {
		return new FlashbackTriggeredAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.sourceId)) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
			if (zEvent.getFromZone() == Constants.Zone.STACK) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "(If the flashback cost was paid, exile this card instead of putting it anywhere else any time it would leave the stack)";
	}

}
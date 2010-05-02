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

package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RemoveDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.sets.Conflux;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class QuenchableFire extends CardImpl {

	public QuenchableFire(UUID ownerId) {
		super(ownerId, "Quenchable Fire", new CardType[]{CardType.SORCERY}, "{3}{R}");
		this.expansionSetId = Conflux.getInstance().getId();
		this.color.setRed(true);
		this.art = "118698_typ_reg_sty_010.jpg";
		this.getSpellAbility().addTarget(new TargetPlayer());
		this.getSpellAbility().addEffect(new DamageTargetEffect(3));
		this.getSpellAbility().addEffect(new QuenchableFireEffect());
	}

}

class QuenchableFireEffect extends OneShotEffect {

	public QuenchableFireEffect() {
		super(Outcome.Damage);
	}
	
	@Override
	public boolean apply(Game game) {
		//create delayed triggereda ability
		QuenchableFireDelayedTriggeredAbility delayedAbility = new QuenchableFireDelayedTriggeredAbility();
		delayedAbility.setSourceId(this.source.getSourceId());
		delayedAbility.setControllerId(this.source.getControllerId());
		delayedAbility.getTargets().addAll(this.source.getTargets());
		game.getState().addDelayedTriggeredAbility(delayedAbility);
		//create special action
		QuenchableFireSpecialAction newAction = new QuenchableFireSpecialAction(delayedAbility.getId());
		delayedAbility.setSpecialActionId(newAction.getId());
		newAction.setSourceId(this.source.getSourceId());
		newAction.setControllerId(this.source.getFirstTarget());
		newAction.getTargets().addAll(this.source.getTargets());
		game.getState().getSpecialActions().add(newAction);
		return true;
	}
	
}

class QuenchableFireDelayedTriggeredAbility extends DelayedTriggeredAbility {

	private UUID specialActionId;

	public QuenchableFireDelayedTriggeredAbility() {
		super(new DamageTargetEffect(3));
	}

	public void setSpecialActionId(UUID specialActionId) {
		this.specialActionId = specialActionId;
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
			trigger(game, event.getPlayerId());
			for (SpecialAction action: game.getState().getSpecialActions()) {
				if (action.getId().equals(specialActionId)) {
					game.getState().getSpecialActions().remove(action);
					break;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "{this} deals an additional 3 damage to that player at the beginning of your next upkeep step unless he or she pays {U} before that step";
	}

}

class QuenchableFireSpecialAction extends SpecialAction {

	public QuenchableFireSpecialAction(UUID effectId) {
		this.addCost(new ManaCosts("{U}"));
		this.addEffect(new RemoveDelayedTriggeredAbilityEffect(effectId));
		this.addEffect(new RemoveSpecialActionEffect(this.getId()));
	}
}
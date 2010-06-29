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

package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.TurnPhase;
import mage.Constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.sets.AlaraReborn;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FinestHour extends CardImpl {

	public FinestHour(UUID ownerId) {
		super(ownerId, "Finest Hour", new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}{U}");
		this.expansionSetId = AlaraReborn.getInstance().getId();
		this.color.setWhite(true);
		this.color.setGreen(true);
		this.color.setBlue(true);
		this.art = "121018_typ_reg_sty_010.jpg";
		this.addAbility(new ExaltedAbility());
		this.addAbility(new FinestHourAbility());
	}

}

class FinestHourAbility extends TriggeredAbilityImpl {

	public FinestHourAbility() {
		super(Zone.BATTLEFIELD, new FinestHourEffect());
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (checkIfClause(game) && game.getActivePlayerId().equals(this.controllerId)) {
			if (event.getType() == EventType.DECLARED_ATTACKERS) {
				if (game.getCombat().attacksAlone()) {
					this.addTarget(new TargetCreaturePermanent());
					this.targets.get(0).getTargets().add(game.getCombat().getAttackers().get(0));
					trigger(game, event.getPlayerId());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkIfClause(Game game) {
		return game.getTurn().getPhase(TurnPhase.COMBAT).getCount() == 0;
	}

	@Override
	public String getRule() {
		return "Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that creature. After this phase, there is an additional combat phase.";
	}

}

class FinestHourEffect extends OneShotEffect {

	public FinestHourEffect() {
		super(Outcome.Benefit);
	}

	@Override
	public boolean apply(Game game) {
		Permanent permanent = game.getPermanent(this.source.getFirstTarget());
		if (permanent != null) {
			permanent.setTapped(false);
			game.getState().getTurnMods().add(new TurnMod(this.source.getControllerId(), TurnPhase.COMBAT, null, false));
		}
		else {
			return false;
		}
		return true;
	}

}
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
import mage.Constants.Rarity;
import mage.Constants.TurnPhase;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;

import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FinestHour extends CardImpl<FinestHour> {

	public FinestHour(UUID ownerId) {
		super(ownerId, 126, "Finest Hour", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}{U}");
		this.expansionSetCode = "ARB";
		this.color.setWhite(true);
		this.color.setGreen(true);
		this.color.setBlue(true);
		this.addAbility(new ExaltedAbility());
		this.addAbility(new FinestHourAbility());
	}

	public FinestHour(final FinestHour card) {
		super(card);
	}

	@Override
	public FinestHour copy() {
		return new FinestHour(this);
	}

	@Override
	public String getArt() {
		return "121018_typ_reg_sty_010.jpg";
	}

}

class FinestHourAbility extends TriggeredAbilityImpl<FinestHourAbility> {

	public FinestHourAbility() {
		super(Zone.BATTLEFIELD, new FinestHourEffect());
	}

	public FinestHourAbility(final FinestHourAbility ability) {
		super(ability);
	}

	@Override
	public FinestHourAbility copy() {
		return new FinestHourAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (checkInterveningIfClause(game) && game.getActivePlayerId().equals(this.controllerId)) {
			if (event.getType() == EventType.DECLARED_ATTACKERS) {
				if (game.getCombat().attacksAlone()) {
					this.addTarget(new TargetCreaturePermanent());
					this.targets.get(0).addTarget(game.getCombat().getAttackers().get(0), this, game);
					trigger(game, event.getPlayerId());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean checkInterveningIfClause(Game game) {
		return game.getTurn().getPhase(TurnPhase.COMBAT).getCount() == 0;
	}

	@Override
	public String getRule() {
		return "Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that creature. After this phase, there is an additional combat phase.";
	}

}

class FinestHourEffect extends OneShotEffect<FinestHourEffect> {

	public FinestHourEffect() {
		super(Outcome.Benefit);
	}

	public FinestHourEffect(final FinestHourEffect effect) {
		super(effect);
	}

	@Override
	public FinestHourEffect copy() {
		return new FinestHourEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			permanent.setTapped(false);
			game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), TurnPhase.COMBAT, null, false));
		}
		else {
			return false;
		}
		return true;
	}

}
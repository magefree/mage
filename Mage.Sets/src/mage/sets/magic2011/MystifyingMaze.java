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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MystifyingMaze extends CardImpl<MystifyingMaze> {

	private static FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature an opponent controls");

	static {
		filter.setTargetController(TargetController.OPPONENT);
	}

	public MystifyingMaze(UUID ownerId) {
		super(ownerId, 226, "Mystifying Maze", Rarity.RARE, new CardType[]{CardType.LAND}, null);
		this.expansionSetCode = "M11";
		this.addAbility(new ColorlessManaAbility());
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MystifyingMazeEffect(), new ManaCostsImpl("{4}"));
		ability.addCost(new TapSourceCost());
		ability.addTarget(new TargetCreaturePermanent(filter));
		this.addAbility(ability);
	}

	public MystifyingMaze(final MystifyingMaze card) {
		super(card);
	}

	@Override
	public MystifyingMaze copy() {
		return new MystifyingMaze(this);
	}

	@Override
	public String getArt() {
		return "129073_typ_reg_sty_010.jpg";
	}
}

class MystifyingMazeEffect extends OneShotEffect<MystifyingMazeEffect> {

	public MystifyingMazeEffect() {
		super(Outcome.Detriment);
	}

	public MystifyingMazeEffect(final MystifyingMazeEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			if (permanent.moveToExile(source.getSourceId(), "Mystifying Maze Exile", source.getId(), game)) {
				//create delayed triggered ability
				MystifyingMazeDelayedTriggeredAbility delayedAbility = new MystifyingMazeDelayedTriggeredAbility(source.getSourceId());
				delayedAbility.setSourceId(source.getSourceId());
				delayedAbility.setControllerId(source.getControllerId());
				game.getState().addDelayedTriggeredAbility(delayedAbility);
				return true;
			}
		}
		return false;
	}

	@Override
	public MystifyingMazeEffect copy() {
		return new MystifyingMazeEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "Exile target attacking creature an opponent controls. At the beginning of the next end step, return it to the battlefield tapped under its owner's control";
	}
}

class MystifyingMazeDelayedTriggeredAbility extends DelayedTriggeredAbility<MystifyingMazeDelayedTriggeredAbility> {

	public MystifyingMazeDelayedTriggeredAbility(UUID exileId) {
		super(new ReturnFromExileEffect(exileId, Zone.BATTLEFIELD, true));
	}

	public MystifyingMazeDelayedTriggeredAbility(final MystifyingMazeDelayedTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public MystifyingMazeDelayedTriggeredAbility copy() {
		return new MystifyingMazeDelayedTriggeredAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.END_TURN_STEP_PRE) {
			trigger(game, this.controllerId);
			return true;
		}
		return false;
	}

}

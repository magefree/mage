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
import mage.Constants.PhaseStep;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Sleep extends CardImpl<Sleep> {

	public Sleep(UUID ownerId) {
		super(ownerId, 71, "Sleep", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
		this.expansionSetCode = "M10";
		this.color.setBlue(true);
		this.getSpellAbility().addTarget(new TargetPlayer());
		this.getSpellAbility().addEffect(new SleepEffect());
	}

	public Sleep(final Sleep card) {
		super(card);
	}

	@Override
	public Sleep copy() {
		return new Sleep(this);
	}
}

class SleepEffect extends OneShotEffect<SleepEffect> {

	public SleepEffect() {
		super(Outcome.Tap);
		staticText = "Tap all creatures target player controls. Those creatures don't untap during that player's next untap step";
	}

	public SleepEffect(final SleepEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null) {
			for (Permanent creature: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId())) {
				creature.tap(game);
				game.addEffect(new SleepEffect2(creature.getId()), source);
			}
			return true;
		}
		return false;
	}

	@Override
	public SleepEffect copy() {
		return new SleepEffect(this);
	}

}

class SleepEffect2 extends ReplacementEffectImpl<SleepEffect2> {

	protected UUID creatureId;

	public SleepEffect2(UUID creatureId) {
		super(Duration.OneUse, Outcome.Detriment);
		this.creatureId = creatureId;
	}

	public SleepEffect2(final SleepEffect2 effect) {
		super(effect);
		creatureId = effect.creatureId;
	}

	@Override
	public SleepEffect2 copy() {
		return new SleepEffect2(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return false;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		used = true;
		return true;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (game.getTurn().getStepType() == PhaseStep.UNTAP &&
				event.getType() == EventType.UNTAP &&
				event.getTargetId().equals(creatureId)) {
			return true;
		}
		return false;
	}

}

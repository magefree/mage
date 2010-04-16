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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.sets.Worldwake;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearingBlaze extends CardImpl {

	public SearingBlaze(UUID ownerId) {
		super(ownerId, "Searing Blaze", new CardType[]{CardType.INSTANT}, "{R}{R}");
		this.expansionSetId = Worldwake.getInstance().getId();
		this.color.setRed(true);
		this.art = "";
		this.getSpellAbility().addTarget(new TargetPlayer());
		//TODO: change this to only allow creatures controlled by first target
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
		this.watchers.add(new SearingBlazeWatcher(ownerId));
	}

}

class SearingBlazeWatcher extends WatcherImpl {

	public SearingBlazeWatcher(UUID controllerId) {
		super(controllerId, "LandPlayed");
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.LAND_PLAYED && game.getOpponents(controllerId).contains(event.getPlayerId()))
			condition = true;
	}

}

class SearingBlazeEffect extends OneShotEffect {

	public SearingBlazeEffect() {
		super(Outcome.Damage);
	}

	@Override
	public boolean apply(Game game) {
		Watcher watcher = game.getState().getWatchers().get(this.source.getControllerId(), "LandPlayed");
		Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
		Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
		if (watcher != null && watcher.conditionMet()) {
			if (player != null) {
				player.damage(3, source.getId(), game);
			}
			if (creature != null) {
				creature.damage(3, source.getId(), game);
			}
		}
		else {
			if (player != null) {
				player.damage(1, source.getId(), game);
			}
			if (creature != null) {
				creature.damage(1, source.getId(), game);
			}
		}
		return true;
	}

}

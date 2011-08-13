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
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SearingBlaze extends CardImpl<SearingBlaze> {

	public SearingBlaze(UUID ownerId) {
		super(ownerId, 90, "Searing Blaze", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}{R}");
		this.expansionSetCode = "WWK";
		this.color.setRed(true);
		this.getSpellAbility().addTarget(new TargetPlayer());
		//TODO: change this to only allow creatures controlled by first target
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
		this.getSpellAbility().addEffect(new SearingBlazeEffect());
		this.addWatcher(new SearingBlazeWatcher());
	}

	public SearingBlaze(final SearingBlaze card) {
		super(card);
	}

	@Override
	public SearingBlaze copy() {
		return new SearingBlaze(this);
	}

}

class SearingBlazeWatcher extends WatcherImpl<SearingBlazeWatcher> {

	public SearingBlazeWatcher() {
		super("LandPlayed");
	}

	public SearingBlazeWatcher(final SearingBlazeWatcher watcher) {
		super(watcher);
	}

	@Override
	public SearingBlazeWatcher copy() {
		return new SearingBlazeWatcher(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).getToZone() == Zone.BATTLEFIELD) {
			Permanent permanent = game.getPermanent(event.getTargetId());
			if (permanent.getCardType().contains(CardType.LAND) && permanent.getControllerId().equals(this.controllerId)) {
				condition = true;
			}
		}
	}

}

class SearingBlazeEffect extends OneShotEffect<SearingBlazeEffect> {

	public SearingBlazeEffect() {
		super(Outcome.Damage);
		staticText = "{this} deals 1 damage to target player and 1 damage to target creature that player controls.  \nLandfall - If you had a land enter the battlefield under your control this turn, {this} deals 3 damage to that player and 3 damage to that creature instead.";
	}

	public SearingBlazeEffect(final SearingBlazeEffect effect) {
		super(effect);
	}

	@Override
	public SearingBlazeEffect copy() {
		return new SearingBlazeEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Watcher watcher = game.getState().getWatchers().get(source.getControllerId(), "LandPlayed");
		Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
		Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
		if (watcher != null && watcher.conditionMet()) {
			if (player != null) {
				player.damage(3, source.getSourceId(), game, false, true);
			}
			if (creature != null) {
				creature.damage(3, source.getSourceId(), game, true, false);
			}
		}
		else {
			if (player != null) {
				player.damage(1, source.getSourceId(), game, false, true);
			}
			if (creature != null) {
				creature.damage(1, source.getSourceId(), game, true, false);
			}
		}
		return true;
	}

}

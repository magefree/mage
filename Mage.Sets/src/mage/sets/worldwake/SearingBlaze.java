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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class SearingBlaze extends CardImpl<SearingBlaze> {

	public SearingBlaze(UUID ownerId) {
		super(ownerId, 90, "Searing Blaze", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}{R}");
		this.expansionSetCode = "WWK";
		this.color.setRed(true);

		// Searing Blaze deals 1 damage to target player and 1 damage to target creature that player controls.
		// Landfall - If you had a land enter the battlefield under your control this turn, Searing Blaze deals 3 damage to that player and 3 damage to that creature instead.
		this.getSpellAbility().addTarget(new TargetPlayer());
		this.getSpellAbility().addTarget(new SearingBlazeTarget());
		this.getSpellAbility().addEffect(new SearingBlazeEffect());
		this.addWatcher(new LandfallWatcher());
	}

	public SearingBlaze(final SearingBlaze card) {
		super(card);
	}

	@Override
	public SearingBlaze copy() {
		return new SearingBlaze(this);
	}

}

class LandfallWatcher extends WatcherImpl<LandfallWatcher> {

	public LandfallWatcher() {
		super("LandPlayed", WatcherScope.PLAYER);
	}

	public LandfallWatcher(final LandfallWatcher watcher) {
		super(watcher);
	}

	@Override
	public LandfallWatcher copy() {
		return new LandfallWatcher(this);
	}

	@Override
	public void watch(GameEvent event, Game game) {
        if (condition == true) //no need to check - condition has already occured
            return;
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
        Watcher watcher = game.getState().getWatchers().get("LandPlayed", source.getControllerId());
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

class SearingBlazeTarget<T extends TargetCreaturePermanent<T>> extends TargetPermanent<TargetCreaturePermanent<T>> {

    public SearingBlazeTarget() {
        super(1, 1, new FilterCreaturePermanent(), false);
    }

    public SearingBlazeTarget(final SearingBlazeTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        UUID firstTarget = source.getFirstTarget();
        Permanent permanent = game.getPermanent(id);
        if (firstTarget != null && permanent != null && permanent.getControllerId().equals(firstTarget)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<UUID>();
        MageObject object = game.getObject(sourceId);
        if (object instanceof StackObject) {
            UUID playerId = ((StackObject)object).getStackAbility().getFirstTarget();
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if(permanent != null && permanent.getControllerId().equals(playerId)){
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public SearingBlazeTarget copy() {
        return new SearingBlazeTarget(this);
    }
}
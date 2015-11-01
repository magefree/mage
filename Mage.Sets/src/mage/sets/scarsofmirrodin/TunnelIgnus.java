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
package mage.sets.scarsofmirrodin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * @author Loki
 */
public class TunnelIgnus extends CardImpl {

    public TunnelIgnus(UUID ownerId) {
        super(ownerId, 105, "Tunnel Ignus", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Elemental");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(new TunnelIgnusTriggeredAbility(), new TunnelIgnusWatcher());
    }

    public TunnelIgnus(final TunnelIgnus card) {
        super(card);
    }

    @Override
    public TunnelIgnus copy() {
        return new TunnelIgnus(this);
    }

}

class TunnelIgnusWatcher extends Watcher {

    protected Map<UUID, Integer> counts = new HashMap<>();

    public TunnelIgnusWatcher() {
        super("LandPlayedCount", WatcherScope.PLAYER);
    }

    public TunnelIgnusWatcher(final TunnelIgnusWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.counts.entrySet()) {
            counts.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public TunnelIgnusWatcher copy() {
        return new TunnelIgnusWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent.getCardType().contains(CardType.LAND) && game.getOpponents(this.controllerId).contains(permanent.getControllerId())) {
                int count = 1;
                if (counts.containsKey(permanent.getControllerId())) {
                    count += counts.get(permanent.getControllerId());
                }
                counts.put(permanent.getControllerId(), count);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        counts.clear();
    }
}

class TunnelIgnusTriggeredAbility extends TriggeredAbilityImpl {

    TunnelIgnusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(3));
    }

    TunnelIgnusTriggeredAbility(final TunnelIgnusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TunnelIgnusTriggeredAbility copy() {
        return new TunnelIgnusTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.getCardType().contains(CardType.LAND) && game.getOpponents(this.controllerId).contains(permanent.getControllerId())) {
            TunnelIgnusWatcher watcher = (TunnelIgnusWatcher) game.getState().getWatchers().get("LandPlayedCount", this.controllerId);
            if (watcher != null && watcher.counts.get(permanent.getControllerId()) > 1) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a land enters the battlefield under an opponent's control, if that player had another land enter the battlefield under his or her control this turn, {this} deals 3 damage to that player.";
    }
}

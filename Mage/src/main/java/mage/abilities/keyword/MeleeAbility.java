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
package mage.abilities.keyword;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

/**
 *
 * @author emerald000
 */
public class MeleeAbility extends AttacksTriggeredAbility {

    public MeleeAbility() {
        super(new BoostSourceEffect(new MeleeDynamicValue(), new MeleeDynamicValue(), Duration.EndOfTurn), false);
        this.addWatcher(new MeleeWatcher());
    }

    public MeleeAbility(final MeleeAbility ability) {
        super(ability);
    }

    @Override
    public MeleeAbility copy() {
        return new MeleeAbility(this);
    }

    @Override
    public String getRule() {
        return "Melee <i>(Whenever this creature attacks, it gets +1/+1 until end of turn for each opponent you attacked with a creature this combat.)</i>";
    }
}

class MeleeWatcher extends Watcher {

    private final HashMap<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    MeleeWatcher() {
        super("MeleeWatcher", WatcherScope.GAME);
    }

    MeleeWatcher(final MeleeWatcher watcher) {
        super(watcher);
        this.playersAttacked.putAll(watcher.playersAttacked);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.BEGIN_COMBAT_STEP_PRE) {
            this.playersAttacked.clear();
        }
        else if (event.getType() == EventType.ATTACKER_DECLARED) {
            Set<UUID> attackedPlayers = this.playersAttacked.getOrDefault(event.getPlayerId(), new HashSet<>(1));
            attackedPlayers.add(event.getTargetId());
            this.playersAttacked.put(event.getPlayerId(), attackedPlayers);
        }
    }

    public int getNumberOfAttackedPlayers(UUID attackerId) {
        return this.playersAttacked.get(attackerId).size();
    }

    @Override
    public MeleeWatcher copy() {
        return new MeleeWatcher(this);
    }
}

class MeleeDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MeleeWatcher watcher = (MeleeWatcher) game.getState().getWatchers().get(MeleeWatcher.class.getSimpleName());
        if (watcher != null) {
            return watcher.getNumberOfAttackedPlayers(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public MeleeDynamicValue copy() {
        return new MeleeDynamicValue();
    }

    @Override
    public String getMessage() {
        return "number of opponents you attacked this combat";
    }

    @Override
    public String toString() {
        return "X";
    }
}

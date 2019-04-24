
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

    private HashMap<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

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
        if (this.playersAttacked.get(attackerId) != null) {
            return this.playersAttacked.get(attackerId).size();
        }
        return 0;
    }

    @Override
    public MeleeWatcher copy() {
        return new MeleeWatcher(this);
    }
}

class MeleeDynamicValue implements DynamicValue {

    private boolean valueChecked = false;
    private int lockedInValue;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MeleeWatcher watcher = (MeleeWatcher) game.getState().getWatchers().get(MeleeWatcher.class.getSimpleName());
        if (watcher != null) {
            if (!valueChecked) {
                this.lockedInValue = watcher.getNumberOfAttackedPlayers(sourceAbility.getControllerId());
                valueChecked = true;
            }
            return this.lockedInValue;
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

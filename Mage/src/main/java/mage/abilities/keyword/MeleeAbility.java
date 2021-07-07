package mage.abilities.keyword;

import java.util.*;

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
        return "melee <i>(Whenever this creature attacks, it gets +1/+1 until end of turn for each opponent you attacked this combat.)</i>";
    }
}

class MeleeWatcher extends Watcher {

    private Map<UUID, Set<UUID>> playersAttacked = new HashMap<>(0);

    public MeleeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.playersAttacked.clear();
        }
        else if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
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
}

class MeleeDynamicValue implements DynamicValue {

    private boolean valueChecked;
    private int lockedInValue;

    public MeleeDynamicValue() {
        super();
    }

    protected MeleeDynamicValue(final MeleeDynamicValue dynamicValue) {
        super();
        valueChecked = dynamicValue.valueChecked;
        lockedInValue = dynamicValue.lockedInValue;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MeleeWatcher watcher = game.getState().getWatcher(MeleeWatcher.class);
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
        return new MeleeDynamicValue(this);
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

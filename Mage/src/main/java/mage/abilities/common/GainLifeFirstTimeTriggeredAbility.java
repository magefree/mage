package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class GainLifeFirstTimeTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ConditionHint(GainLifeCondition.instance);
    private final boolean when;

    public GainLifeFirstTimeTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect, false, false);
    }

    public GainLifeFirstTimeTriggeredAbility(Zone zone, Effect effect, boolean optional, boolean when) {
        super(zone, effect, optional);
        this.when = when;
        this.addWatcher(new GainLifeFirstTimeWatcher());
        this.addHint(hint);
        setTriggerPhrase("When" + (when ? "" : "ever") + " you gain life for the first time each turn, ");
    }

    private GainLifeFirstTimeTriggeredAbility(final GainLifeFirstTimeTriggeredAbility ability) {
        super(ability);
        this.when = ability.when;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        GainLifeFirstTimeWatcher watcher = game.getState().getWatcher(GainLifeFirstTimeWatcher.class);
        return watcher != null && watcher.checkEvent(getControllerId(), event);
    }

    @Override
    public GainLifeFirstTimeTriggeredAbility copy() {
        return new GainLifeFirstTimeTriggeredAbility(this);
    }
}

enum GainLifeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        GainLifeFirstTimeWatcher watcher = game.getState().getWatcher(GainLifeFirstTimeWatcher.class);
        return watcher != null && watcher.checkGain(source.getControllerId());
    }

    @Override
    public String toString() {
        return "You haven't gained life this turn";
    }
}

class GainLifeFirstTimeWatcher extends Watcher {

    private final Map<UUID, UUID> gainedMap = new HashMap<>();

    GainLifeFirstTimeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.GAINED_LIFE
                || event.getAmount() < 1) {
            return;
        }
        gainedMap.putIfAbsent(event.getPlayerId(), event.getId());
    }

    @Override
    public void reset() {
        super.reset();
        gainedMap.clear();
    }

    boolean checkEvent(UUID playerId, GameEvent event) {
        return event.getId().equals(gainedMap.getOrDefault(playerId, null));
    }

    boolean checkGain(UUID playerId) {
        return !gainedMap.containsKey(playerId);
    }
}

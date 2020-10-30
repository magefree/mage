package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class DrawSecondCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );

    public DrawSecondCardTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.addWatcher(new DrawSecondCardWatcher());
        this.addHint(hint);
    }

    private DrawSecondCardTriggeredAbility(final DrawSecondCardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DrawSecondCardWatcher watcher = game.getState().getWatcher(DrawSecondCardWatcher.class);
        return watcher != null && watcher.checkEvent(getControllerId(), event);
    }

    @Override
    public String getRule() {
        return "Whenever you draw your second card each turn, " + super.getRule();
    }

    @Override
    public DrawSecondCardTriggeredAbility copy() {
        return new DrawSecondCardTriggeredAbility(this);
    }
}

class DrawSecondCardWatcher extends Watcher {

    private final Set<UUID> drewOnce = new HashSet<>();
    private final Map<UUID, UUID> secondDrawMap = new HashMap<>();

    DrawSecondCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DREW_CARD) {
            return;
        }
        if (drewOnce.contains(event.getPlayerId())) {
            secondDrawMap.putIfAbsent(event.getPlayerId(), event.getId());
        } else {
            drewOnce.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        drewOnce.clear();
        secondDrawMap.clear();
    }

    boolean checkEvent(UUID playerId, GameEvent event) {
        return event.getId().equals(secondDrawMap.getOrDefault(playerId, null));
    }
}

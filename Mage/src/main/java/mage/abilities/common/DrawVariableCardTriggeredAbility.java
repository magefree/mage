package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author AustinYQM
 */
public class DrawVariableCardTriggeredAbility extends TriggeredAbilityImpl {
    private static final String[] drawVerbiage = new String[]{ "Zeroth", "First", "Second", "Third", "Fourth", "Fifth", "Sixth", "Seventh", "Eighth", "Ninth", "Tenth" };
    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;
    private final int targetDrawCount;

    public DrawVariableCardTriggeredAbility(Effect effect,  boolean optional) {
        this(effect, 1, optional, TargetController.YOU);
    }
    public DrawVariableCardTriggeredAbility(Effect effect, int targetDrawCount, boolean optional) {
        this(effect, targetDrawCount, optional, TargetController.YOU);
    }

    public DrawVariableCardTriggeredAbility(Effect effect, int targetDrawCount, boolean optional, TargetController targetController) {
        this(Zone.BATTLEFIELD, effect, targetDrawCount, optional, targetController);
    }

    public DrawVariableCardTriggeredAbility(Zone zone, Effect effect, int targetDrawCount, boolean optional, TargetController targetController) {
        super(zone, effect, optional);
        this.addWatcher(new DrawVariableCardWatcher(targetDrawCount));
        this.targetController = targetController;
        this.targetDrawCount = targetDrawCount;
        this.addHint(hint);
        setTriggerPhrase(generateTriggerPhrase());
    }

    private DrawVariableCardTriggeredAbility(final DrawVariableCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.targetDrawCount = ability.targetDrawCount;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                break;
            case ACTIVE:
                if (!game.isActivePlayer(event.getPlayerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                Player controller = game.getPlayer(controllerId);
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
        return DrawVariableCardWatcher.checkEvent(event.getPlayerId(), event, game);
    }

    public String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you draw your " + drawVerbiage[targetDrawCount] + " card each turn, ";
            case ACTIVE:
                return "Whenever a player draws their  " + drawVerbiage[targetDrawCount] + "  card during their turn, ";
            case OPPONENT:
                return "Whenever an opponent draws their  " + drawVerbiage[targetDrawCount] + "  card each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
    }

    @Override
    public DrawVariableCardTriggeredAbility copy() {
        return new DrawVariableCardTriggeredAbility(this);
    }
}

class DrawVariableCardWatcher extends Watcher {
    private final int targetDrawnCount;
    private final Map<UUID, LongAdder> draws = new HashMap<>();
    private final Map<UUID, UUID> conditionMeetMap = new HashMap<>();

    DrawVariableCardWatcher(int targetDrawnCount) {
        super(WatcherScope.GAME);
        this.targetDrawnCount = targetDrawnCount;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DREW_CARD) {
            return;
        }
        draws.putIfAbsent(event.getPlayerId(), new LongAdder());
        draws.get(event.getPlayerId()).increment();
        if(draws.get(event.getPlayerId()).intValue() == targetDrawnCount) {
            conditionMeetMap.putIfAbsent(event.getPlayerId(), event.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        draws.clear();
        conditionMeetMap.clear();
    }

    static boolean checkEvent(UUID playerId, GameEvent event, Game game) {
        return event.getId().equals(game.getState().getWatcher(DrawVariableCardWatcher.class).conditionMeetMap.getOrDefault(playerId, null));
    }
}

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

/**
 * @author TheElk801
 */
public class DrawSecondCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;

    public DrawSecondCardTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, TargetController.YOU);
    }

    public DrawSecondCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController) {
        this(Zone.BATTLEFIELD, effect, optional, targetController);
    }

    public DrawSecondCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController) {
        super(zone, effect, optional);
        this.addWatcher(new DrawSecondCardWatcher());
        this.targetController = targetController;
        this.addHint(hint);
        setTriggerPhrase(generateTriggerPhrase());
    }

    private DrawSecondCardTriggeredAbility(final DrawSecondCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
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
        return DrawSecondCardWatcher.checkEvent(event.getPlayerId(), event, game);
    }

    public String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you draw your second card each turn, ";
            case ACTIVE:
                return "Whenever a player draws their second card during their turn, ";
            case OPPONENT:
                return "Whenever an opponent draws their second card each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
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

    static boolean checkEvent(UUID playerId, GameEvent event, Game game) {
        return event.getId().equals(game.getState().getWatcher(DrawSecondCardWatcher.class).secondDrawMap.getOrDefault(playerId, null));
    }
}

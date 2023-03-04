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
public class DrawCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;
    private final DrawCardWatcher drawCardWatcher = new DrawCardWatcher();
    private final Integer cardNumber;

    public DrawCardTriggeredAbility(Effect effect, boolean optional, Integer cardNumber) {
        this(effect, optional, TargetController.YOU, cardNumber);
    }

    public DrawCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController, Integer cardNumber) {
        this(Zone.BATTLEFIELD, effect, optional, targetController, cardNumber);
    }

    public DrawCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, Integer cardNumber) {
        super(zone, effect, optional);
        this.addWatcher(this.drawCardWatcher);
        this.targetController = targetController;
        this.cardNumber = cardNumber;
        this.addHint(hint);
        setTriggerPhrase(generateTriggerPhrase());
    }

    private DrawCardTriggeredAbility(final DrawCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.cardNumber = ability.cardNumber;
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
        return drawCardWatcher.checkEvent(event.getPlayerId(), event, cardNumber);
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
    public DrawCardTriggeredAbility copy() {
        return new DrawCardTriggeredAbility(this);
    }
}

class DrawCardWatcher extends Watcher {

    private final Map<UUID, SortedSet<UUID>> drawMap = new HashMap<>();

    DrawCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DREW_CARD) {
            return;
        }
        if (!drawMap.containsKey(event.getPlayerId())) {
            drawMap.putIfAbsent(event.getPlayerId(), new TreeSet<>());
        }
        drawMap.get(event.getPlayerId()).add(event.getId());
    }

    @Override
    public void reset() {
        super.reset();
        drawMap.clear();
    }
    
    public boolean checkEvent(UUID playerId, GameEvent event, Integer cardNumber) {
        return this.drawMap.containsKey(playerId) && Objects.equals(this.drawMap.get(playerId).size(), cardNumber) && event.getId().equals(this.drawMap.get(playerId).last());
    }
    
}

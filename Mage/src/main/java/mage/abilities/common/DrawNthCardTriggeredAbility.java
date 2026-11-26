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
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class DrawNthCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;
    private final int cardNumber;
    private final boolean setTargetPointer;

    public DrawNthCardTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, 2);
    }

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional, int cardNumber) {
        this(effect, optional, TargetController.YOU, cardNumber);
    }

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController, int cardNumber) {
        this(Zone.BATTLEFIELD, effect, optional, targetController, cardNumber);
    }

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController, int cardNumber, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, targetController, cardNumber, setTargetPointer);
    }

    public DrawNthCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, int cardNumber) {
        this(zone, effect, optional, targetController, cardNumber, false);
    }

    public DrawNthCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, int cardNumber, boolean setTargetPointer) {
        super(zone, effect, optional);
        this.targetController = targetController;
        this.cardNumber = cardNumber;
        this.setTargetPointer = setTargetPointer;
        if (targetController == TargetController.YOU) {
            this.addHint(hint);
        }
        setTriggerPhrase(generateTriggerPhrase());
        this.addWatcher(new DrawNthCardWatcher());
    }

    protected DrawNthCardTriggeredAbility(final DrawNthCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.cardNumber = ability.cardNumber;
        this.setTargetPointer = ability.setTargetPointer;
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
                if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
                    return false;
                }
                break;
            case ANY:
                // Doesn't matter who
                break;
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
        if (DrawNthCardWatcher.checkEvent(event.getPlayerId(), event.getId(), game) + 1 != cardNumber) {
            return false;
        }
        if (setTargetPointer) {
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        }
        return true;
    }

    public String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you draw your " + CardUtil.numberToOrdinalText(cardNumber) + " card each turn, ";
            case ACTIVE:
                return "Whenever a player draws their " + CardUtil.numberToOrdinalText(cardNumber) + " card during their turn, ";
            case OPPONENT:
                return "Whenever an opponent draws their " + CardUtil.numberToOrdinalText(cardNumber) + " card each turn, ";
            case ANY:
                return "Whenever a player draws their " + CardUtil.numberToOrdinalText(cardNumber) + " card each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
    }

    @Override
    public DrawNthCardTriggeredAbility copy() {
        return new DrawNthCardTriggeredAbility(this);
    }
}

class DrawNthCardWatcher extends Watcher {

    private final Map<UUID, List<UUID>> playerDrawEventMap = new HashMap<>();

    DrawNthCardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD) {
            playerDrawEventMap
                    .computeIfAbsent(event.getPlayerId(), x -> new ArrayList<>())
                    .add(event.getId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerDrawEventMap.clear();
    }

    static int checkEvent(UUID playerId, UUID eventId, Game game) {
        return game
                .getState()
                .getWatcher(DrawNthCardWatcher.class)
                .playerDrawEventMap
                .getOrDefault(playerId, Collections.emptyList())
                .indexOf(eventId);
    }
}

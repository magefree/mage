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
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public class DrawNthOrNthCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;
    private final int firstCardNumber;
    private final int secondCardNumber;

    public DrawNthOrNthCardTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public DrawNthOrNthCardTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, 1);
    }

    public DrawNthOrNthCardTriggeredAbility(Effect effect, boolean optional, int firstCardNumber) {
        this(effect, optional, TargetController.YOU, firstCardNumber);
    }

    public DrawNthOrNthCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController, int firstCardNumber) {
        this(Zone.BATTLEFIELD, effect, optional, targetController, firstCardNumber, 2);
    }

    public DrawNthOrNthCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, int firstCardNumber, int secondCardNumber) {
        super(zone, effect, optional);
        this.targetController = targetController;
        this.firstCardNumber = firstCardNumber;
        this.secondCardNumber = secondCardNumber;
        if (targetController == TargetController.YOU) {
            this.addHint(hint);
        }
        setTriggerPhrase(generateTriggerPhrase());
        this.addWatcher(new DrawNthOrNthCardWatcher());
    }

    protected DrawNthOrNthCardTriggeredAbility(final DrawNthOrNthCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.firstCardNumber = ability.firstCardNumber;
        this.secondCardNumber = ability.secondCardNumber;
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
        int drawnCards = DrawNthOrNthCardWatcher.checkEvent(event.getPlayerId(), event.getId(), game) + 1;
        return  drawnCards == firstCardNumber || drawnCards == secondCardNumber;
    }

    public String generateTriggerPhrase() {
        String numberText = CardUtil.numberToOrdinalText(firstCardNumber) + " or " + CardUtil.numberToOrdinalText(secondCardNumber);
        switch (targetController) {
            case YOU:
                return "Whenever you draw your " + numberText + " card each turn, ";
            case ACTIVE:
                return "Whenever a player draws their " + numberText + " card during their turn, ";
            case OPPONENT:
                return "Whenever an opponent draws their " + numberText + " card each turn, ";
            case ANY:
                return "Whenever a player draws their " + numberText + " card each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
    }

    @Override
    public DrawNthOrNthCardTriggeredAbility copy() {
        return new DrawNthOrNthCardTriggeredAbility(this);
    }
}

class DrawNthOrNthCardWatcher extends Watcher {

    private final Map<UUID, List<UUID>> playerDrawEventMap = new HashMap<>();

    DrawNthOrNthCardWatcher() {
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
                .getWatcher(DrawNthOrNthCardWatcher.class)
                .playerDrawEventMap
                .getOrDefault(playerId, Collections.emptyList())
                .indexOf(eventId);
    }
}

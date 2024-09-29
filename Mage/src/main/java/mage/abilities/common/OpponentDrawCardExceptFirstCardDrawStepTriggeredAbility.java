package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 * @author AsterAether, Susucr
 */
public class OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility extends TriggeredAbilityImpl {

    public OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(Zone zone, Effect effect, Boolean optional) {
        super(zone, effect, optional);
        this.addWatcher(new CardsDrawnDuringDrawStepWatcher());
        setTriggerPhrase("Whenever an opponent draws a card except the first one they draw in each of their draw steps, ");
    }

    public OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            if (game.isActivePlayer(event.getPlayerId())
                    && game.getPhase().getStep().getType() == PhaseStep.DRAW) {
                CardsDrawnDuringDrawStepWatcher watcher = game.getState().getWatcher(CardsDrawnDuringDrawStepWatcher.class);
                return watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 1;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility copy() {
        return new OpponentDrawCardExceptFirstCardDrawStepTriggeredAbility(this);
    }
}

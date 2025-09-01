package mage.abilities.common.delayed;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Susucr
 */
public class AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private GameEvent.EventType stepEvent;
    private int nextTurn = -1; // once the controller starts a new turn, register it to trigger that turn.
    private boolean isActive = true;

    public AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(Effect effect, GameEvent.EventType stepEvent) {
        this(effect, stepEvent, false);
    }

    public AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(Effect effect, GameEvent.EventType stepEvent, boolean optional) {
        super(effect, Duration.Custom, true, optional);
        this.stepEvent = stepEvent;
        this.setTriggerPhrase(generateTriggerPhrase());
    }

    private AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(final AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility ability) {
        super(ability);
        this.nextTurn = ability.nextTurn;
        this.isActive = ability.isActive;
        this.stepEvent = ability.stepEvent;
    }

    @Override
    public AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility copy() {
        return new AtTheBeginOfStepOfYourNextTurnDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == stepEvent
                || event.getType() == GameEvent.EventType.BEGIN_TURN;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            // not your turn.
            return false;
        }

        int turn = game.getTurnNum();
        switch (event.getType()) {
            case BEGIN_TURN:
                // We register the turn number at the start of your next turn.
                // This is in order to not trigger if that turn ends without an end step.
                if (this.nextTurn == -1) {
                    this.nextTurn = turn;
                } else if (turn > this.nextTurn) {
                    this.isActive = false; // to have the delayed trigger being cleaned up
                }
                return false;
            default:
                return turn == this.nextTurn && event.getType() == stepEvent;
        }
    }

    @Override
    public boolean isInactive(Game game) {
        return super.isInactive(game) || !isActive;
    }

    private String generateTriggerPhrase() {
        switch (stepEvent) {
            case END_TURN_STEP_PRE:
                return "At the beginning of the end step on your next turn, ";
            case DECLARE_ATTACKERS_STEP_PRE:
                return "At the beginning of the declare attackers step on your next turn, ";
        }
        throw new IllegalArgumentException("stepEvent only supports steps events");
    }
}

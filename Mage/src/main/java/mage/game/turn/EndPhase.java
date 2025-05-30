

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EndPhase extends Phase {

    public EndPhase() {
        this.type = TurnPhase.END;
        this.event = EventType.END_PHASE;
        this.preEvent = EventType.END_PHASE_PRE;
        this.postEvent = EventType.END_PHASE_POST;
        this.steps.add(new EndStep());
        this.steps.add(new CleanupStep());
    }

    protected EndPhase(final EndPhase phase) {
        super(phase);
    }

    @Override
    protected void playStep(Game game) {
        if (currentStep.getType() == PhaseStep.CLEANUP) {
            game.getState().increaseStepNum();
            game.getTurn().setEndTurnRequested(false); // so triggers trigger again
            prePriority(game, activePlayerId);

            // 514.3.
            // Normally, no player receives priority during the cleanup step, so no spells can be cast and
            // no abilities can be activated. However, this rule is subject to the following exception:
            // 514.3a
            // At this point, the game checks to see if any state-based actions would be performed
            // and/or any triggered abilities are waiting to be put onto the stack (including those that 
            // trigger "at the beginning of the next cleanup step"). If so, those state-based actions are 
            // performed, then those triggered abilities are put on the stack, then the active player gets
            // priority. Players may cast spells and activate abilities. Once the stack is empty and all players
            // pass in succession, another cleanup step begins
            if (game.checkStateAndTriggered()) {
                game.informPlayers("State-based actions or triggers happened on cleanup step, so players get priority due 514.3a");
                // queues a new cleanup step and request new priorities
                game.getState().getTurnMods().add(new TurnMod(activePlayerId).withExtraStep(new CleanupStep()));
                if (!game.isPaused() && !game.checkIfGameIsOver() && !game.executingRollback()) {
                    currentStep.priority(game, activePlayerId, false);
                    if (game.executingRollback()) {
                        return;
                    }
                }
            }
            if (!game.isPaused() && !game.checkIfGameIsOver() && !game.executingRollback()) {
                postPriority(game, activePlayerId);
            }
        } else {
            super.playStep(game);
        }
    }

    @Override
    public EndPhase copy() {
        return new EndPhase(this);
    }

}


package mage.game.turn;

import java.io.Serializable;
import java.util.UUID;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 * Game's step
 *
 * Warning, don't use a changeable data in step's implementations
 * TODO: implement copyable<> interface and copy usage in GameState
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class Step implements Serializable {

    private final PhaseStep type;
    private final boolean hasPriority;
    protected EventType stepEvent;
    protected EventType preStepEvent;
    protected EventType postStepEvent;
    protected StepPart stepPart;

    public enum StepPart {
        PRE, PRIORITY, POST
    }

    public abstract Step copy();

    public Step(PhaseStep type, boolean hasPriority) {
        this.type = type;
        this.hasPriority = hasPriority;
    }

    public Step(final Step step) {
        this.type = step.type;
        this.hasPriority = step.hasPriority;
        this.stepEvent = step.stepEvent;
        this.preStepEvent = step.preStepEvent;
        this.postStepEvent = step.postStepEvent;
        this.stepPart = step.stepPart;
    }

    public PhaseStep getType() {
        return type;
    }

    public void beginStep(Game game, UUID activePlayerId) {
        stepPart = StepPart.PRE;
        game.fireEvent(new GameEvent(preStepEvent, null, null, activePlayerId));
    }

    public void resumeBeginStep(Game game, UUID activePlayerId) {
        stepPart = StepPart.PRE;
    }

    public void priority(Game game, UUID activePlayerId, boolean resuming) {
        if (hasPriority) {
            stepPart = StepPart.PRIORITY;
            game.fireEvent(new GameEvent(stepEvent, null, null, activePlayerId));
            game.playPriority(activePlayerId, resuming);
        }
    }

    public void endStep(Game game, UUID activePlayerId) {
        stepPart = StepPart.POST;
        game.fireEvent(new GameEvent(postStepEvent, null, null, activePlayerId));
    }

    public boolean skipStep(Game game, UUID activePlayerId) {
        return game.replaceEvent(new GameEvent(stepEvent, null, null, activePlayerId));
    }

    public boolean getHasPriority() {
        return this.hasPriority;
    }

    public StepPart getStepPart() {
        return stepPart;
    }

}

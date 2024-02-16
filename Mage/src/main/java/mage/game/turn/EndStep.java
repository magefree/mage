

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EndStep extends Step {

    public EndStep() {
        super(PhaseStep.END_TURN, true);
        this.stepEvent = EventType.END_TURN_STEP;
        this.preStepEvent = EventType.END_TURN_STEP_PRE;
        this.postStepEvent = EventType.END_TURN_STEP_POST;
    }

    protected EndStep(final EndStep step) {
        super(step);
    }

    @Override
    public EndStep copy() {
        return new EndStep(this);
    }

}

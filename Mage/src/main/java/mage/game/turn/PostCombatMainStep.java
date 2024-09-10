

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class PostCombatMainStep extends Step {

    public PostCombatMainStep() {
        super(PhaseStep.POSTCOMBAT_MAIN, true);
        this.stepEvent = EventType.POSTCOMBAT_MAIN_STEP;
        this.preStepEvent = EventType.POSTCOMBAT_MAIN_STEP_PRE;
        this.postStepEvent = EventType.POSTCOMBAT_MAIN_STEP_POST;
    }

    protected PostCombatMainStep(final PostCombatMainStep step) {
        super(step);
    }

    @Override
    public PostCombatMainStep copy() {
        return new PostCombatMainStep(this);
    }

}

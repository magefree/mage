

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

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

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);

        game.getState().removeBoESEffects(game);
    }
}

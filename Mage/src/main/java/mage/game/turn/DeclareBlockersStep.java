
package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DeclareBlockersStep extends Step {

    public DeclareBlockersStep() {
        super(PhaseStep.DECLARE_BLOCKERS, true);
        this.stepEvent = EventType.DECLARE_BLOCKERS_STEP;
        this.preStepEvent = EventType.DECLARE_BLOCKERS_STEP_PRE;
        this.postStepEvent = EventType.DECLARE_BLOCKERS_STEP_POST;
    }

    protected DeclareBlockersStep(final DeclareBlockersStep step) {
        super(step);
    }

    @Override
    public boolean skipStep(Game game, UUID activePlayerId) {
        if (game.getCombat().noAttackers()) {
            return true;
        }
        return super.skipStep(game, activePlayerId);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        game.getCombat().selectBlockers(game);
        if (!game.isPaused() && !game.executingRollback()) {
            game.getCombat().acceptBlockers(game);
        }
    }

    @Override
    public void resumeBeginStep(Game game, UUID activePlayerId) {
        super.resumeBeginStep(game, activePlayerId);
        game.getCombat().resumeSelectBlockers(game);
        game.getCombat().acceptBlockers(game);
    }

    @Override
    public DeclareBlockersStep copy() {
        return new DeclareBlockersStep(this);
    }

}

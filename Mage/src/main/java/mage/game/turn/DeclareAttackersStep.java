

package mage.game.turn;

import java.util.UUID;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class DeclareAttackersStep extends Step {

    public DeclareAttackersStep() {
        super(PhaseStep.DECLARE_ATTACKERS, true);
        this.stepEvent = EventType.DECLARE_ATTACKERS_STEP;
        this.preStepEvent = EventType.DECLARE_ATTACKERS_STEP_PRE;
        this.postStepEvent = EventType.DECLARE_ATTACKERS_STEP_POST;
    }

    protected DeclareAttackersStep(final DeclareAttackersStep step) {
        super(step);
    }

    @Override
    public boolean skipStep(Game game, UUID activePlayerId) {
        return super.skipStep(game, activePlayerId);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        game.getTurn().setDeclareAttackersStepStarted(true);
        game.getCombat().selectAttackers(game);
    }

    @Override
    public void resumeBeginStep(Game game, UUID activePlayerId) {
        super.resumeBeginStep(game, activePlayerId);
        game.getCombat().resumeSelectAttackers(game);
    }

    @Override
    public DeclareAttackersStep copy() {
        return new DeclareAttackersStep(this);
    }

}

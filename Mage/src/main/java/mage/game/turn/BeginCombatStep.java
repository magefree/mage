

package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BeginCombatStep extends Step {

    public BeginCombatStep() {
        super(PhaseStep.BEGIN_COMBAT, true);
        this.stepEvent = EventType.BEGIN_COMBAT_STEP;
        this.preStepEvent = EventType.BEGIN_COMBAT_STEP_PRE;
        this.postStepEvent = EventType.BEGIN_COMBAT_STEP_POST;
    }

    protected BeginCombatStep(final BeginCombatStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        //20091005 - 507.1
        game.getCombat().clear();
        game.getCombat().setAttacker(activePlayerId);
        game.getCombat().setDefenders(game);
        super.beginStep(game, activePlayerId);
    }

    @Override
    public BeginCombatStep copy() {
        return new BeginCombatStep(this);
    }

    public String toString() {
        return "begin combat";
    }
}



package mage.game.turn;

import java.util.UUID;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EndOfCombatStep extends Step {

    public EndOfCombatStep() {
        super(PhaseStep.END_COMBAT, true);
        this.stepEvent = EventType.END_COMBAT_STEP;
        this.preStepEvent = EventType.END_COMBAT_STEP_PRE;
        this.postStepEvent = EventType.END_COMBAT_STEP_POST;
    }

    protected EndOfCombatStep(final EndOfCombatStep step) {
        super(step);
    }

    @Override
    public void endStep(Game game, UUID activePlayerId) {
        super.endStep(game, activePlayerId);
        //20091005 - 511.3
        game.getCombat().endCombat(game);
        game.getState().removeEocEffects(game);
//        game.saveState();
    }

    @Override
    public EndOfCombatStep copy() {
        return new EndOfCombatStep(this);
    }

}

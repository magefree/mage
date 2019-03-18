

package mage.game.turn;

import java.util.UUID;
import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class UntapStep extends Step {

    public UntapStep() {
        super(PhaseStep.UNTAP, false);
        this.stepEvent = EventType.UNTAP_STEP;
        this.preStepEvent = EventType.UNTAP_STEP_PRE;
        this.postStepEvent = EventType.UNTAP_STEP_POST;
    }

    public UntapStep(final UntapStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        Player activePlayer = game.getPlayer(activePlayerId);
        //20091005 - 502.1/703.4a
        activePlayer.phasing(game);
        //20091005 - 502.2/703.4b
        activePlayer.untap(game);
        game.applyEffects();
    }

    @Override
    public UntapStep copy() {
        return new UntapStep(this);
    }

}

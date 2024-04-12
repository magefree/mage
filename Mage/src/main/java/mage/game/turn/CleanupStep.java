

package mage.game.turn;

import java.util.UUID;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CleanupStep extends Step {

    public CleanupStep() {
        super(PhaseStep.CLEANUP, true);
        this.stepEvent = EventType.CLEANUP_STEP;
        this.preStepEvent = EventType.CLEANUP_STEP_PRE;
        this.postStepEvent = EventType.CLEANUP_STEP_POST;
    }

    protected CleanupStep(final CleanupStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        super.beginStep(game, activePlayerId);
        Player activePlayer = game.getPlayer(activePlayerId);
        game.getState().setPriorityPlayerId(activePlayer.getId());
        //20091005 - 514.1
        if (activePlayer.isInGame()) {
            activePlayer.discardToMax(game);
        }
        //20100423 - 514.2
        game.getBattlefield().endOfTurn(activePlayerId, game);
        game.getState().removeEotEffects(game);
    }

    @Override
    public void endStep(Game game, UUID activePlayerId) {
        Player activePlayer = game.getPlayer(activePlayerId);
        activePlayer.setGameUnderYourControl(true);
        super.endStep(game, activePlayerId);
    }

    @Override
    public CleanupStep copy() {
        return new CleanupStep(this);
    }

}

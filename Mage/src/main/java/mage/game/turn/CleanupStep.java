package mage.game.turn;

import mage.constants.PhaseStep;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

import java.util.UUID;

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

        // 514.1
        // First, if the active player’s hand contains more cards than his or her maximum hand size
        // (normally seven), he or she discards enough cards to reduce his or her hand size to that number.
        // This turn-based action doesn’t use the stack.
        if (activePlayer.isInGame()) {
            activePlayer.discardToMax(game);
        }

        // 514.2
        // Second, the following actions happen simultaneously: all damage marked on permanents
        // (including phased-out permanents) is removed and all "until end of turn" and "this turn"
        // effects end. This turn-based action doesn’t use the stack.
        game.getBattlefield().endOfTurn(game);
        game.getState().removeEotEffects(game);

        // 514.3
        // Normally, no player receives priority during the cleanup step, so no spells can be cast
        // and no abilities can be activated. However, this rule is subject to the following exception: 514.3a
        //
        // Look at EndPhase code to process 514.3
    }

    @Override
    public void endStep(Game game, UUID activePlayerId) {
        super.endStep(game, activePlayerId);
    }

    @Override
    public CleanupStep copy() {
        return new CleanupStep(this);
    }

}

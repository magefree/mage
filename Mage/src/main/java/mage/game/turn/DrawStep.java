

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
public class DrawStep extends Step {

    public DrawStep() {
        super(PhaseStep.DRAW, true);
        this.stepEvent = EventType.DRAW_STEP;
        this.preStepEvent = EventType.DRAW_STEP_PRE;
        this.postStepEvent = EventType.DRAW_STEP_POST;
    }

    public DrawStep(final DrawStep step) {
        super(step);
    }

    @Override
    public void beginStep(Game game, UUID activePlayerId) {
        Player activePlayer = game.getPlayer(activePlayerId);
        //20091005 - 504.1/703.4c
        activePlayer.drawCards(1, game);
//        game.saveState();
        game.applyEffects();
        super.beginStep(game, activePlayerId);
    }

    @Override
    public DrawStep copy() {
        return new DrawStep(this);
    }


}



package mage.game.turn;

import java.util.UUID;

import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BeginningPhase extends Phase {

    public BeginningPhase() {
        this.type = TurnPhase.BEGINNING;
        this.event = EventType.BEGINNING_PHASE;
        this.preEvent = EventType.BEGINNING_PHASE_PRE;
        this.postEvent = EventType.BEGINNING_PHASE_POST;
        this.steps.add(new UntapStep());
        this.steps.add(new UpkeepStep());
        this.steps.add(new DrawStep());
    }

    @Override
    public boolean beginPhase(Game game, UUID activePlayerId) {
        game.getBattlefield().beginningOfTurn(game);
        return super.beginPhase(game, activePlayerId);
    }


    protected BeginningPhase(final BeginningPhase phase) {
        super(phase);
    }

    @Override
    public BeginningPhase copy() {
        return new BeginningPhase(this);
    }

}

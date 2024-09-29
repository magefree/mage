

package mage.game.turn;

import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.events.GameEvent.EventType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class BeginningPhase extends Phase {

    private final boolean isExtra;

    public BeginningPhase() {
        this(false);
    }

    public BeginningPhase(boolean isExtra) {
        this.type = TurnPhase.BEGINNING;
        this.event = isExtra ? EventType.BEGINNING_PHASE_EXTRA : EventType.BEGINNING_PHASE;
        this.preEvent = isExtra ? EventType.BEGINNING_PHASE_PRE_EXTRA : EventType.BEGINNING_PHASE_PRE;
        this.postEvent = isExtra ? EventType.BEGINNING_PHASE_PRE_EXTRA : EventType.BEGINNING_PHASE_POST;
        this.steps.add(new UntapStep());
        this.steps.add(new UpkeepStep());
        this.steps.add(new DrawStep());
        this.isExtra = isExtra;
    }

    @Override
    public boolean beginPhase(Game game, UUID activePlayerId) {
        if (!isExtra) {
            game.getBattlefield().beginningOfTurn(game);
        }
        return super.beginPhase(game, activePlayerId);
    }

    protected BeginningPhase(final BeginningPhase phase) {
        super(phase);
        this.isExtra = phase.isExtra;
    }

    @Override
    public BeginningPhase copy() {
        return new BeginningPhase(this);
    }

}

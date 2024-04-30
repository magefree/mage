package mage.game.events;

import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PhaseChangedEvent extends GameEvent {

    public PhaseChangedEvent(UUID playerId, TurnMod extraTurnMode) {
        super(GameEvent.EventType.PHASE_CHANGED, playerId, null, playerId);
        this.setSourceId(extraTurnMode == null ? null : extraTurnMode.getId());
    }
}

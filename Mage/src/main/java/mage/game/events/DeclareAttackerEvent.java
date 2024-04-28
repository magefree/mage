package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DeclareAttackerEvent extends GameEvent {

    public DeclareAttackerEvent(UUID targetId, UUID attackerId, UUID attackerControllerId) {
        super(GameEvent.EventType.DECLARE_ATTACKER, targetId, attackerId, attackerControllerId);
    }
}

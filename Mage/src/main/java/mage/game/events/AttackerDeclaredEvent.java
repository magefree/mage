package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class AttackerDeclaredEvent extends GameEvent {

    public AttackerDeclaredEvent(UUID targetId, UUID attackerId, UUID attackerControllerId) {
        super(GameEvent.EventType.ATTACKER_DECLARED, targetId, null, attackerControllerId);
        this.setSourceId(attackerId);
    }
}

package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class BlockerDeclaredEvent extends GameEvent {

    public BlockerDeclaredEvent(UUID attackerId, UUID blockerId, UUID blockerControllerId) {
        super(GameEvent.EventType.BLOCKER_DECLARED, attackerId, null, blockerControllerId);
        this.setSourceId(blockerId);
    }
}

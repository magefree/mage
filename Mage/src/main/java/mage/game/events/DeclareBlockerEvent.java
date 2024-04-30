package mage.game.events;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class DeclareBlockerEvent extends GameEvent {

    public DeclareBlockerEvent(UUID attackerId, UUID blockerId, UUID blockerControllerId) {
        super(GameEvent.EventType.DECLARE_BLOCKER, attackerId, null, blockerControllerId);
        this.setSourceId(blockerId);
    }
}

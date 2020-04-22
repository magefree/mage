package mage.game.events;

import mage.game.permanent.PermanentToken;

import java.util.UUID;

public class CreatedTokenEvent extends GameEvent {

    public CreatedTokenEvent(UUID sourceId, PermanentToken tokenPerm) {
        super(EventType.CREATED_TOKEN, tokenPerm.getId(), sourceId, tokenPerm.getControllerId());
    }
}

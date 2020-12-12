package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.PermanentToken;

public class CreatedTokenEvent extends GameEvent {

    public CreatedTokenEvent(Ability source, PermanentToken tokenPerm) {
        super(GameEvent.EventType.CREATED_TOKEN, tokenPerm.getId(), source, tokenPerm.getControllerId());
    }
}

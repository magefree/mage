package mage.game.events;

import mage.abilities.Ability;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class MadnessCardExiledEvent extends GameEvent {

    public MadnessCardExiledEvent(UUID cardId, Ability source, UUID controllerId) {
        super(GameEvent.EventType.MADNESS_CARD_EXILED, cardId, null, controllerId);
        this.setSourceId(source.getOriginalId()); // save ability's id
    }
}

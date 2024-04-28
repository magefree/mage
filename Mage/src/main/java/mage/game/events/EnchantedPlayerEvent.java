package mage.game.events;

import mage.abilities.Ability;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class EnchantedPlayerEvent extends GameEvent {

    // TODO: investigate why source is provided but not used at all?
    public EnchantedPlayerEvent(UUID targetId, Permanent attachment, Ability source) {
        super(GameEvent.EventType.ENCHANTED_PLAYER, targetId, attachment.getId(), attachment.getControllerId());
    }
}

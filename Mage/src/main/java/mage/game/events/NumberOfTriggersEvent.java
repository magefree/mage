

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NumberOfTriggersEvent extends GameEvent {

    private final GameEvent sourceEvent;

    public NumberOfTriggersEvent(UUID controllerOfAbilityId, UUID sourceOfTrigger, GameEvent sourceEvent) {
        super(EventType.NUMBER_OF_TRIGGERS, null, sourceOfTrigger, controllerOfAbilityId);
        this.sourceEvent = sourceEvent;
        this.amount = 1; // Number of times to trigger. Panharmonicon can change this.
    }

    public GameEvent getSourceEvent() {
        return sourceEvent;
    }
}

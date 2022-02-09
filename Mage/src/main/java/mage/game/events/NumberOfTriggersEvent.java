package mage.game.events;

import mage.abilities.Ability;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NumberOfTriggersEvent extends GameEvent {

    private final GameEvent sourceEvent;

    public NumberOfTriggersEvent(Ability triggeredAbility, GameEvent sourceEvent) {
        super(GameEvent.EventType.NUMBER_OF_TRIGGERS, null, triggeredAbility, triggeredAbility.getControllerId());
        this.sourceEvent = sourceEvent;
        this.amount = 1; // Number of times to trigger. Panharmonicon can change this.
    }

    public GameEvent getSourceEvent() {
        return sourceEvent;
    }
}

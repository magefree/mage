package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author oscscull
 */
public class UnlockThisDoorTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean isLeftHalf;

    public UnlockThisDoorTriggeredAbility(Effect effect, boolean optional, boolean isLeftHalf) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.isLeftHalf = isLeftHalf;
        this.setTriggerPhrase("When you unlock this door, ");
    }

    private UnlockThisDoorTriggeredAbility(final UnlockThisDoorTriggeredAbility ability) {
        super(ability);
        this.isLeftHalf = ability.isLeftHalf;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return isLeftHalf ? event.getType() == GameEvent.EventType.UNLOCK_LEFT_DOOR
                : event.getType() == GameEvent.EventType.UNLOCK_RIGHT_DOOR;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // Trigger when this door's specific half is unlocked
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public UnlockThisDoorTriggeredAbility copy() {
        return new UnlockThisDoorTriggeredAbility(this);
    }
}
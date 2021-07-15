package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public class BecomesBlockedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesBlockedAttachedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesBlockedAttachedTriggeredAbility(final BecomesBlockedAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null 
                && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            return (equipped != null
                    && equipped.getId().equals(event.getTargetId()));
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever enchanted creature becomes blocked, " ;
    }

    @Override
    public BecomesBlockedAttachedTriggeredAbility copy() {
        return new BecomesBlockedAttachedTriggeredAbility(this);
    }
}

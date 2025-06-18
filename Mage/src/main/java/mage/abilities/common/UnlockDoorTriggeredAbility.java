
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.UnlockDoorEvent;

/**
 * @author oscscull
 */
public class UnlockDoorTriggeredAbility extends TriggeredAbilityImpl {
    
    public UnlockDoorTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When you unlock this door, ");
    }
    
    public UnlockDoorTriggeredAbility(final UnlockDoorTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public UnlockDoorTriggeredAbility copy() {
        return new UnlockDoorTriggeredAbility(this);
    }
    
   @Override
    public boolean checkEventType(GameEvent event, Game game) {
        System.out.println("EVENT TYPE"); // This should now print!
        System.out.println(event);
        return event.getType() == GameEvent.EventType.UNLOCK_DOOR;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        System.out.println("CHECK YOUR TRIGGER"); // This should now print!
        System.out.println(event);
        System.out.println(event.getPlayerId());
        System.out.println(event.getTargetId());
        
        // NO CAST NEEDED HERE: Use the generic GameEvent directly
        return event.getTargetId().equals(getSourceId()) && 
               event.getPlayerId().equals(getControllerId());
    }
}
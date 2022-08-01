
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class AuraAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public AuraAttachedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever an Aura becomes attached to {this}, ");
    }

    public AuraAttachedTriggeredAbility(final AuraAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent attachment = game.getPermanent(event.getSourceId());
            if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuraAttachedTriggeredAbility copy() {
        return new AuraAttachedTriggeredAbility(this);
    }
    
}

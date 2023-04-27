

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.events.GameEvent;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author jeffwadsworth
 */
public class BecomesTargetControllerSpellTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesTargetControllerSpellTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When you become the target of a spell, ");
    }

    public BecomesTargetControllerSpellTriggeredAbility(final BecomesTargetControllerSpellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTargetControllerSpellTriggeredAbility copy() {
        return new BecomesTargetControllerSpellTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(controllerId)) {
            if (game.getObject(event.getSourceId()) instanceof Spell) {
                return true;
            }
        }
        return false;
    }
}

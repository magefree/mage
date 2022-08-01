
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author nantuko
 */
public class BecomesTappedSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesTappedSourceTriggeredAbility(Effect effect, boolean isOptional) {
        super(Zone.BATTLEFIELD, effect, isOptional);
    }

    public BecomesTappedSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} becomes tapped, ");
    }

    public BecomesTappedSourceTriggeredAbility(final BecomesTappedSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTappedSourceTriggeredAbility copy() {
        return new BecomesTappedSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(sourceId);
    }
}

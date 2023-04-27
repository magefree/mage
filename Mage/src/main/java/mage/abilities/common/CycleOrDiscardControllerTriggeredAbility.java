
package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author fireshoes
 */
public class CycleOrDiscardControllerTriggeredAbility extends TriggeredAbilityImpl {

    public CycleOrDiscardControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CycleOrDiscardControllerTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever you cycle or discard a card, ");
    }

    public CycleOrDiscardControllerTriggeredAbility(final CycleOrDiscardControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public CycleOrDiscardControllerTriggeredAbility copy() {
        return new CycleOrDiscardControllerTriggeredAbility(this);
    }
}

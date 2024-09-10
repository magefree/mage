package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class ProliferatedControllerTriggeredAbility extends TriggeredAbilityImpl {

    public ProliferatedControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public ProliferatedControllerTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public ProliferatedControllerTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you proliferate, ");
    }

    private ProliferatedControllerTriggeredAbility(final ProliferatedControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PROLIFERATED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public ProliferatedControllerTriggeredAbility copy() {
        return new ProliferatedControllerTriggeredAbility(this);
    }
}

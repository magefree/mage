package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class ScryTriggeredAbility extends TriggeredAbilityImpl {

    public ScryTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public ScryTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public ScryTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever you scry, ");
    }

    private ScryTriggeredAbility(final ScryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScryTriggeredAbility copy() {
        return new ScryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (isControlledBy(event.getPlayerId())) {
            this.getEffects().setValue("amount", event.getAmount());
            return true;
        }
        return false;
    }
}

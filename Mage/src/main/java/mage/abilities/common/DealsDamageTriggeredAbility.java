package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */

public class DealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever {this} deals damage, ");
    }

    protected DealsDamageTriggeredAbility(final DealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageTriggeredAbility copy() {
        return new DealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }
}

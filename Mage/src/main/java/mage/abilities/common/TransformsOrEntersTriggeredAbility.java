package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class TransformsOrEntersTriggeredAbility extends TriggeredAbilityImpl {
    public TransformsOrEntersTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever this creature enters or transforms into {this}, ");
    }

    private TransformsOrEntersTriggeredAbility(final TransformsOrEntersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TransformsOrEntersTriggeredAbility copy() {
        return new TransformsOrEntersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }
}

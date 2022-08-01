package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class TransformIntoSourceTriggeredAbility extends TriggeredAbilityImpl {

    public TransformIntoSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public TransformIntoSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public TransformIntoSourceTriggeredAbility(Effect effect, boolean optional, boolean whenever) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When" + (whenever ? "ever" : "") + " this creature transforms into {this}, ");
    }

    private TransformIntoSourceTriggeredAbility(final TransformIntoSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TransformIntoSourceTriggeredAbility copy() {
        return new TransformIntoSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.isTransformed();
    }
}

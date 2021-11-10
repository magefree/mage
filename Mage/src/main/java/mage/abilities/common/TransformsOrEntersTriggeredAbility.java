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
public class TransformsOrEntersTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean whenever;

    public TransformsOrEntersTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public TransformsOrEntersTriggeredAbility(Effect effect, boolean optional, boolean whenever) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.whenever = whenever;
    }

    private TransformsOrEntersTriggeredAbility(final TransformsOrEntersTriggeredAbility ability) {
        super(ability);
        this.whenever = ability.whenever;
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
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        switch (event.getType()) {
            case TRANSFORMED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                return permanent != null && !permanent.isTransformed();
            case ENTERS_THE_BATTLEFIELD:
                return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When" + (whenever ? "ever" : "") + " this creature enters the battlefield or transforms into {this}, ";
    }
}

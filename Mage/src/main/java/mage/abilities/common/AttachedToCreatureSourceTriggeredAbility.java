package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author htrajan
 */
public class AttachedToCreatureSourceTriggeredAbility extends TriggeredAbilityImpl {

    public AttachedToCreatureSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("As {this} becomes attached to a creature, ");
    }

    public AttachedToCreatureSourceTriggeredAbility(final AttachedToCreatureSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACHED
                && event.getSourceId() != null
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attachedPermanent = game.getPermanent(event.getTargetId());
        return attachedPermanent != null && attachedPermanent.isCreature(game);
    }

    @Override
    public AttachedToCreatureSourceTriggeredAbility copy() {
        return new AttachedToCreatureSourceTriggeredAbility(this);
    }
}

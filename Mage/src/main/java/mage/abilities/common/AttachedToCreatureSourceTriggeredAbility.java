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
        setTriggerPhrase("Whenever {this} becomes attached to a creature, ");
    }

    public AttachedToCreatureSourceTriggeredAbility(final AttachedToCreatureSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        Permanent attachedPermanent = game.getPermanent(event.getTargetId());
        if (attachedPermanent == null || !attachedPermanent.isCreature(game)) {
            return false;
        }
        this.getEffects().setValue("attachedPermanent", attachedPermanent);
        return true;
    }

    @Override
    public AttachedToCreatureSourceTriggeredAbility copy() {
        return new AttachedToCreatureSourceTriggeredAbility(this);
    }
}

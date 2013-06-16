package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class AttacksEquippedTriggeredAbility extends TriggeredAbilityImpl<AttacksEquippedTriggeredAbility> {
    public AttacksEquippedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public AttacksEquippedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public AttacksEquippedTriggeredAbility(final AttacksEquippedTriggeredAbility abiltity) {
        super(abiltity);
    }

    @Override
    public AttacksEquippedTriggeredAbility copy() {
        return new AttacksEquippedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.sourceId);
        if (equipment != null && equipment.getAttachedTo() != null
                && event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                && event.getSourceId().equals(equipment.getAttachedTo())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, " + super.getRule();
    }
}

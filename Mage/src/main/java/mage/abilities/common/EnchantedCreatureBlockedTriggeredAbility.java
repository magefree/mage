package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class EnchantedCreatureBlockedTriggeredAbility extends TriggeredAbilityImpl {

    public EnchantedCreatureBlockedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public EnchantedCreatureBlockedTriggeredAbility(final EnchantedCreatureBlockedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped.getId().equals(event.getTargetId())) {
                getEffects().get(1).setTargetPointer(new FixedTarget(equipped, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever enchanted creature becomes blocked, " ;
    }

    @Override
    public EnchantedCreatureBlockedTriggeredAbility copy() {
        return new EnchantedCreatureBlockedTriggeredAbility(this);
    }
}

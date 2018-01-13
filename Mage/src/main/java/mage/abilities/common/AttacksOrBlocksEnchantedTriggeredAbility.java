// @author jeffwadsworth

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class AttacksOrBlocksEnchantedTriggeredAbility extends TriggeredAbilityImpl {

    public AttacksOrBlocksEnchantedTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect);
    }

    public AttacksOrBlocksEnchantedTriggeredAbility(final AttacksOrBlocksEnchantedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksOrBlocksEnchantedTriggeredAbility copy() {
        return new AttacksOrBlocksEnchantedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks or blocks, "+ super.getRule();
    }
}

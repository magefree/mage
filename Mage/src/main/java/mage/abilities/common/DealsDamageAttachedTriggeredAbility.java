/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */

public class DealsDamageAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageAttachedTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    public DealsDamageAttachedTriggeredAbility(final DealsDamageAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageAttachedTriggeredAbility copy() {
        return new DealsDamageAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        if (enchanted != null && event.getSourceId().equals(enchanted.getId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals damage, " + super.getRule();
    }
}

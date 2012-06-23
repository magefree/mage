/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Jeff
 */
public class BecomesTappedCreatureControlledTriggeredAbility extends TriggeredAbilityImpl<BecomesTappedCreatureControlledTriggeredAbility>{
    
    public BecomesTappedCreatureControlledTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesTappedCreatureControlledTriggeredAbility(final BecomesTappedCreatureControlledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesTappedCreatureControlledTriggeredAbility copy() {
        return new BecomesTappedCreatureControlledTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED && game.getPermanent(event.getTargetId()).getControllerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a creature you control becomes tapped, " + super.getRule();
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Jeff
 */
public class BecomesTappedCreatureControlledTriggeredAbility extends TriggeredAbilityImpl{
    
    FilterControlledCreaturePermanent filter;
    
    public BecomesTappedCreatureControlledTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterControlledCreaturePermanent("a creature you control"));
    }
    public BecomesTappedCreatureControlledTriggeredAbility(Effect effect, boolean optional, FilterControlledCreaturePermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public BecomesTappedCreatureControlledTriggeredAbility(final BecomesTappedCreatureControlledTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public BecomesTappedCreatureControlledTriggeredAbility copy() {
        return new BecomesTappedCreatureControlledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "When " + filter.getMessage() + " becomes tapped, " + super.getRule();
    }    
}

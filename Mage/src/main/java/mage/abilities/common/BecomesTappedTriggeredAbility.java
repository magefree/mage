/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Jeff
 */
public class BecomesTappedTriggeredAbility extends TriggeredAbilityImpl{

    FilterPermanent filter;

    public BecomesTappedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterPermanent("a permanent"));
    }
    public BecomesTappedTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public BecomesTappedTriggeredAbility(final BecomesTappedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public BecomesTappedTriggeredAbility copy() {
        return new BecomesTappedTriggeredAbility(this);
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
        return "Whenever " + filter.getMessage() + " becomes tapped, " + super.getRule();
    }
}

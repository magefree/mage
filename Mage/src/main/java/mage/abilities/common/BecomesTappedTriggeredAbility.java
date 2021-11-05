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
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class BecomesTappedTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected boolean setTargetPointer;

    public BecomesTappedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterPermanent("a permanent"));
    }

    public BecomesTappedTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        this(effect, optional, filter, false);
    }

    public BecomesTappedTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public BecomesTappedTriggeredAbility(final BecomesTappedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
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
        if (filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " becomes tapped, " ;
    }
}

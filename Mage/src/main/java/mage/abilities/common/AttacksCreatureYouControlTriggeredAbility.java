
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author noxx
 */
public class AttacksCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterControlledCreaturePermanent filter;
    protected boolean setTargetPointer;
    protected boolean once = false;

    public AttacksCreatureYouControlTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterControlledCreaturePermanent());
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, new FilterControlledCreaturePermanent(), setTargetPointer);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterControlledCreaturePermanent filter) {
        this(effect, optional, filter, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterControlledCreaturePermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public AttacksCreatureYouControlTriggeredAbility(AttacksCreatureYouControlTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (sourcePermanent != null && filter.match(sourcePermanent, sourceId, controllerId, game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public AttacksCreatureYouControlTriggeredAbility copy() {
        return new AttacksCreatureYouControlTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When" + (once ? "" : "ever") + " a" + (filter.getMessage().startsWith("a") ? "n " : " ") + filter.getMessage() + " attacks, " + super.getRule();
    }
}


package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class BecomesBlockedAllTriggeredAbility extends TriggeredAbilityImpl {

    private FilterCreaturePermanent filter;
    private boolean setTargetPointer;

    public BecomesBlockedAllTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent("a creature"), false);
    }

    public BecomesBlockedAllTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public BecomesBlockedAllTriggeredAbility(final BecomesBlockedAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " becomes blocked, " + super.getRule();
    }

    @Override
    public BecomesBlockedAllTriggeredAbility copy() {
        return new BecomesBlockedAllTriggeredAbility(this);
    }
}

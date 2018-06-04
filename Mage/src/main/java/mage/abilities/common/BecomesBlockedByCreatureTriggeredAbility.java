
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
 * @author North
 */
public class BecomesBlockedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public BecomesBlockedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BecomesBlockedByCreatureTriggeredAbility(Effect effect, FilterCreaturePermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public BecomesBlockedByCreatureTriggeredAbility(final BecomesBlockedByCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (filter.match(blocker, game)) {
                this.getEffects().setTargetPointer(new FixedTarget(blocker, game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes blocked by "
                + (filter.getMessage().startsWith("an ") ? "" : "a ")
                + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public BecomesBlockedByCreatureTriggeredAbility copy() {
        return new BecomesBlockedByCreatureTriggeredAbility(this);
    }
}

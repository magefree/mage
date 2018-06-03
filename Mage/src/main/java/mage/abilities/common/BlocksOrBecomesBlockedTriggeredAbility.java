
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North, Loki
 */
public class BlocksOrBecomesBlockedTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;
    protected boolean setTargetPointer;

    public BlocksOrBecomesBlockedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional, null, true);
    }

    public BlocksOrBecomesBlockedTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, filter, optional, null, true);
    }

    public BlocksOrBecomesBlockedTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(effect, filter, optional, rule, true);
    }

    public BlocksOrBecomesBlockedTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.setTargetPointer = setTargetPointer;
    }

    public BlocksOrBecomesBlockedTriggeredAbility(final BlocksOrBecomesBlockedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.setTargetPointer = ability.setTargetPointer;

    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            Permanent blocked = game.getPermanent(event.getTargetId());
            if (blocked != null && filter.match(blocked, game)) {
                if (setTargetPointer) {
                    this.getEffects().setTargetPointer(new FixedTarget(blocked, game));
                }
                return true;
            }
        }
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (blocker != null && filter.match(blocker, game)) {
                if (setTargetPointer) {
                    this.getEffects().setTargetPointer(new FixedTarget(blocker, game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null) {
            return rule;
        }
        return "Whenever {this} blocks or becomes blocked" + (setTargetPointer ? " by a " + filter.getMessage() : "") + ", " + super.getRule();
    }

    @Override
    public BlocksOrBecomesBlockedTriggeredAbility copy() {
        return new BlocksOrBecomesBlockedTriggeredAbility(this);
    }
}

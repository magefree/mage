
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
 * @author North, Loki
 */
public class BlocksOrBecomesBlockedSourceTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    protected String rule;
    protected boolean setTargetPointer;

    public BlocksOrBecomesBlockedSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, true);
    }

    public BlocksOrBecomesBlockedSourceTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional, null, setTargetPointer);
    }

    public BlocksOrBecomesBlockedSourceTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        this(effect, filter, optional, null, true);
    }

    public BlocksOrBecomesBlockedSourceTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(effect, filter, optional, rule, true);
    }

    public BlocksOrBecomesBlockedSourceTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, String rule, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.rule = rule;
        this.setTargetPointer = setTargetPointer;
    }

    public BlocksOrBecomesBlockedSourceTriggeredAbility(final BlocksOrBecomesBlockedSourceTriggeredAbility ability) {
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
            if (filter.match(blocked, game)) {
                if (setTargetPointer) {
                    this.getEffects().setTargetPointer(new FixedTarget(blocked, game));
                }
                return true;
            }
        }
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent blocker = game.getPermanent(event.getSourceId());
            if (filter.match(blocker, game)) {
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
        return super.getRule();
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks or becomes blocked" + (setTargetPointer ? " by a " + filter.getMessage() : "") + ", ";
    }

    @Override
    public BlocksOrBecomesBlockedSourceTriggeredAbility copy() {
        return new BlocksOrBecomesBlockedSourceTriggeredAbility(this);
    }
}

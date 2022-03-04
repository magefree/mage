package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public class BecomesBlockedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public BecomesBlockedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional);
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
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        Permanent blocker = game.getPermanent(event.getSourceId());
        if (!filter.match(blocker, game)) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} becomes blocked by "
                + (filter.getMessage().startsWith("an ") ? "" : "a ")
                + filter.getMessage() + ", " ;
    }

    @Override
    public BecomesBlockedByCreatureTriggeredAbility copy() {
        return new BecomesBlockedByCreatureTriggeredAbility(this);
    }
}

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
 * @author LevelX2
 */
public class BecomesBlockedAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public BecomesBlockedAllTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_A_CREATURE, false);
    }

    public BecomesBlockedAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + filter.getMessage() + " becomes blocked, ");
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
        if (filter.match(permanent, getControllerId(), this, game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public BecomesBlockedAllTriggeredAbility copy() {
        return new BecomesBlockedAllTriggeredAbility(this);
    }
}

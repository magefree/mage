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
 * @author TheElk801
 */
public class SacrificePermanentTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public SacrificePermanentTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_PERMANENT_A);
    }

    public SacrificePermanentTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public SacrificePermanentTriggeredAbility(Effect effect, FilterPermanent filter, boolean setTargetPointer) {
        this(effect, filter, setTargetPointer, false);
    }

    public SacrificePermanentTriggeredAbility(Effect effect, FilterPermanent filter, boolean setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setLeavesTheBattlefieldTrigger(true);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public SacrificePermanentTriggeredAbility(final SacrificePermanentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public SacrificePermanentTriggeredAbility copy() {
        return new SacrificePermanentTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (!isControlledBy(event.getPlayerId()) || permanent == null
                || !filter.match(permanent, getSourceId(), getControllerId(), game)) {
            return false;
        }
        this.getEffects().setValue("sacrificedPermanent", permanent);
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you sacrifice " + filter.getMessage() + ", ";
    }
}

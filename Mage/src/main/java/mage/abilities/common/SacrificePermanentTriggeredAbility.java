package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        super(Zone.BATTLEFIELD, effect);
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
        if (isControlledBy(event.getPlayerId())
                && filter.match(game.getPermanentOrLKIBattlefield(event.getTargetId()), getSourceId(), getControllerId(), game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you sacrifice " + filter.getMessage() + ", ";
    }
}

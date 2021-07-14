
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class SacrificePermanentTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;

    public SacrificePermanentTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_PERMANENT_A);
    }

    public SacrificePermanentTriggeredAbility(Effect effect, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect);
        setLeavesTheBattlefieldTrigger(true);
        this.filter = filter;
    }

    public SacrificePermanentTriggeredAbility(final SacrificePermanentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
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
        return isControlledBy(event.getPlayerId())
                && filter.match(game.getPermanentOrLKIBattlefield(event.getTargetId()), getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice " + filter.getMessage() + ", " + super.getRule();
    }
}


package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 *
 * @author North
 */
public class BecomesTargetTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;

    public BecomesTargetTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_OR_ABILITY);
    }

    public BecomesTargetTriggeredAbility(Effect effect, FilterStackObject filter) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter.copy();
    }

    public BecomesTargetTriggeredAbility(final BecomesTargetTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
    }

    @Override
    public BecomesTargetTriggeredAbility copy() {
        return new BecomesTargetTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        return event.getTargetId().equals(getSourceId()) && filter.match(sourceObject, getSourceId(), getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "When {this} becomes the target of " + filter.getMessage() + ", " + super.getRule();
    }
}

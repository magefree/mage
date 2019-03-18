

package mage.abilities.common;

import mage.constants.Zone;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class SimpleTriggeredAbility extends TriggeredAbilityImpl {

    private EventType eventType;
    private boolean onlyController;
    private String prefix;

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix) {
        this(zone, eventType, effect, prefix, false);
    }

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix, boolean onlyController) {
        this(zone, eventType, effect, prefix, onlyController, false);
    }

    public SimpleTriggeredAbility(Zone zone, EventType eventType, Effect effect, String prefix, boolean onlyController, boolean optional) {
        super(zone, effect, optional);
        this.eventType = eventType;
        this.onlyController = onlyController;
        this.prefix = prefix;
    }

    public SimpleTriggeredAbility(final SimpleTriggeredAbility ability) {
        super(ability);
        this.eventType = ability.eventType;
        this.onlyController = ability.onlyController;
        this.prefix = ability.prefix;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == eventType;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !onlyController || event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public SimpleTriggeredAbility copy() {
        return new SimpleTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return prefix + super.getRule();
    }
}

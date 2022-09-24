
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class OnEventTriggeredAbility extends TriggeredAbilityImpl {

    private final EventType eventType;
    private final String eventName;
    private final boolean allPlayers;

    public OnEventTriggeredAbility(EventType eventType, String eventName, Effect effect) {
        this(eventType, eventName, effect, false);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, Effect effect, boolean optional) {
        this(eventType, eventName, false, effect, optional);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, boolean allPlayers, Effect effect) {
        this(eventType, eventName, allPlayers, effect, false);
    }

    public OnEventTriggeredAbility(EventType eventType, String eventName, boolean allPlayers, Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.eventType = eventType;
        this.eventName = eventName;
        this.allPlayers = allPlayers;
        setTriggerPhrase("At the " + eventName + ", ");
    }

    public OnEventTriggeredAbility(final OnEventTriggeredAbility ability) {
        super(ability);
        this.eventType = ability.eventType;
        this.eventName = ability.eventName;
        this.allPlayers = ability.allPlayers;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == eventType;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return allPlayers || event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public OnEventTriggeredAbility copy() {
        return new OnEventTriggeredAbility(this);
    }

}

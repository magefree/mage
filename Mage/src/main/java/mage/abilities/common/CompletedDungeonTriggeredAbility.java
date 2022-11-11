package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class CompletedDungeonTriggeredAbility extends TriggeredAbilityImpl {

    public CompletedDungeonTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CompletedDungeonTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public CompletedDungeonTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("Whenever you complete a dungeon, ");
    }

    private CompletedDungeonTriggeredAbility(final CompletedDungeonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DUNGEON_COMPLETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public CompletedDungeonTriggeredAbility copy() {
        return new CompletedDungeonTriggeredAbility(this);
    }
}

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author awjackson
 */
public class BlocksOrBlockedSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksOrBlockedSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }
    public BlocksOrBlockedSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever {this} blocks or becomes blocked, ");
    }

    public BlocksOrBlockedSourceTriggeredAbility(final BlocksOrBlockedSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS
                || event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public BlocksOrBlockedSourceTriggeredAbility copy() {
        return new BlocksOrBlockedSourceTriggeredAbility(this);
    }
}

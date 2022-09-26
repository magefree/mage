package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author awjackson
 */
public class BlocksOrBlockedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }
    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever enchanted creature blocks or becomes blocked, ");
    }

    public BlocksOrBlockedAttachedTriggeredAbility(final BlocksOrBlockedAttachedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS
                || event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature == null || !creature.getAttachments().contains(getSourceId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(creature, game));
        return true;
    }

    @Override
    public BlocksOrBlockedAttachedTriggeredAbility copy() {
        return new BlocksOrBlockedAttachedTriggeredAbility(this);
    }
}

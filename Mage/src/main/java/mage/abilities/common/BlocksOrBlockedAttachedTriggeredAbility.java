package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author awjackson
 */
public class BlocksOrBlockedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    // Type of attachment: AURA or EQUIPMENT
    private final AttachmentType attachmentType;

    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType) {
        this(effect, attachmentType, false);
    }

    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature blocks or becomes blocked, ");
        this.attachmentType = attachmentType;
    }

    public BlocksOrBlockedAttachedTriggeredAbility(final BlocksOrBlockedAttachedTriggeredAbility ability) {
        super(ability);
        this.attachmentType = ability.attachmentType;
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

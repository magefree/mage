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
 * @author awjackson & TiagoMDG
 */
public class BlocksOrBlockedAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final AttachmentType attachmentType;
    private final boolean selfTarget;

    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean selfTarget) {
        this(effect, attachmentType, selfTarget, false);
    }

    public BlocksOrBlockedAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean selfTarget, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature blocks or becomes blocked, ");
        this.attachmentType = attachmentType;
        this.selfTarget = selfTarget;
    }

    public BlocksOrBlockedAttachedTriggeredAbility(final BlocksOrBlockedAttachedTriggeredAbility ability) {
        super(ability);
        this.attachmentType = ability.attachmentType;
        this.selfTarget = ability.selfTarget;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        Permanent equipment = game.getPermanent(sourceId);

        if (creature == null || !creature.getAttachments().contains(getSourceId())) {
            return false;
        }

        if (!selfTarget) {
            if (event.getSourceId().equals(equipment.getAttachedTo())) {
                Permanent blocks = game.getPermanent(event.getTargetId());
                getEffects().setTargetPointer(new FixedTarget(blocks, game));
            }

            if (event.getTargetId().equals(equipment.getAttachedTo())) {
                Permanent blockedBy = game.getPermanent(event.getSourceId());
                getEffects().setTargetPointer(new FixedTarget(blockedBy, game));
            }
            return true;
        }

        getEffects().setTargetPointer(new FixedTarget(creature, game));
        return true;
    }

    @Override
    public BlocksOrBlockedAttachedTriggeredAbility copy() {
        return new BlocksOrBlockedAttachedTriggeredAbility(this);
    }
}

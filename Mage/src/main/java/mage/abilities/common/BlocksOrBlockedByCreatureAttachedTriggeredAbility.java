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
 * @author TiagoMDG
 */
public class BlocksOrBlockedByCreatureAttachedTriggeredAbility extends TriggeredAbilityImpl {

    // Type of attachment AURA or EQUIPMENT
    private final AttachmentType attachmentType;

    // Controls if the effect should target the creature with the attachment or the blocking/blocked creature
    private final boolean selfTarget;

    public BlocksOrBlockedByCreatureAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean selfTarget) {
        this(effect, attachmentType, selfTarget, false);
    }

    public BlocksOrBlockedByCreatureAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean selfTarget, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever " + attachmentType.verb().toLowerCase() + " creature blocks or becomes blocked by a creature, ");
        this.attachmentType = attachmentType;
        this.selfTarget = selfTarget;
    }

    public BlocksOrBlockedByCreatureAttachedTriggeredAbility(final BlocksOrBlockedByCreatureAttachedTriggeredAbility ability) {
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
        Permanent blockingCreature = game.getPermanent(event.getSourceId());
        Permanent blockedCreature = game.getPermanent(event.getTargetId());
        Permanent attachment = game.getPermanent(sourceId);

        if (attachment == null || blockingCreature == null || blockedCreature == null) {
            return false;
        }

        Permanent attachedCreature = game.getPermanent(attachment.getAttachedTo());

        if (attachedCreature == null) {
            return false;
        }

        if (!selfTarget) {
            if (blockedCreature.equals(attachedCreature)) {
                getEffects().setTargetPointer(new FixedTarget(blockingCreature, game));
                return true;
            }

            if (blockingCreature.equals(attachedCreature)) {
                getEffects().setTargetPointer(new FixedTarget(blockedCreature, game));
                return true;
            }
        } else {
            if (blockedCreature.equals(attachedCreature)) {
                getEffects().setTargetPointer(new FixedTarget(blockedCreature, game));
                return true;
            }

            if (blockingCreature.equals(attachedCreature)) {
                getEffects().setTargetPointer(new FixedTarget(blockingCreature, game));
                return true;
            }
        }
        return false;
    }


    @Override
    public BlocksOrBlockedByCreatureAttachedTriggeredAbility copy() {
        return new BlocksOrBlockedByCreatureAttachedTriggeredAbility(this);
    }
}


package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Locale;

/**
 * "When enchanted/equipped creature attacks " triggered ability
 *
 * @author LevelX2
 */
public class AttacksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private AttachmentType attachmentType;
    private final boolean setTargetPointer;

    public AttacksAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, AttachmentType.EQUIPMENT, optional);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional) {
        this(effect, attachmentType, optional, false);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.attachmentType = attachmentType;
        this.setTargetPointer = setTargetPointer;
    }

    public AttacksAttachedTriggeredAbility(final AttacksAttachedTriggeredAbility abiltity) {
        super(abiltity);
        this.attachmentType = abiltity.attachmentType;
        this.setTargetPointer = abiltity.setTargetPointer;
    }

    @Override
    public AttacksAttachedTriggeredAbility copy() {
        return new AttacksAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.sourceId);
        if (equipment != null && equipment.getAttachedTo() != null
                && event.getSourceId().equals(equipment.getAttachedTo())) {
            getEffects().setValue("sourceId", event.getSourceId());
            // TODO: Passing a permanent object like this can cause bugs. May need refactoring to use UUID instead.
            // See https://github.com/magefree/mage/issues/8377
            // 11-08-2021: Added a new constructor to set target pointer. Should probably be using this instead.
            Permanent attachedPermanent = game.getPermanent(event.getSourceId());
            getEffects().setValue("attachedPermanent", attachedPermanent);
            if (setTargetPointer && attachedPermanent != null) {
                getEffects().setTargetPointer(new FixedTarget(attachedPermanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        StringBuilder sb = new StringBuilder("Whenever ");
        sb.append(attachmentType.verb().toLowerCase(Locale.ENGLISH));
        return sb.append(" creature attacks, ").toString();
    }
}

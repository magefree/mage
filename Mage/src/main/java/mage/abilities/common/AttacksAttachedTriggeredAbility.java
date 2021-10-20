
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AttachmentType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Locale;

/**
 * "When enchanted/equipped creature attacks " triggered ability
 *
 * @author LevelX2
 */
public class AttacksAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private AttachmentType attachmentType;

    public AttacksAttachedTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, boolean optional) {
        this(effect, AttachmentType.EQUIPMENT, optional);
    }

    public AttacksAttachedTriggeredAbility(Effect effect, AttachmentType attachmentType, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.attachmentType = attachmentType;
    }

    public AttacksAttachedTriggeredAbility(final AttacksAttachedTriggeredAbility abiltity) {
        super(abiltity);
        this.attachmentType = abiltity.attachmentType;
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
            getEffects().setValue("attachedPermanent", game.getPermanent(event.getSourceId()));
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

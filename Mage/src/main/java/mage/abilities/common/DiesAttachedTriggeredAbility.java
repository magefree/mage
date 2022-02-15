package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * "When enchanted/equipped creature dies" triggered ability
 *
 * @author Loki
 */
public class DiesAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final String attachedDescription;
    private final boolean diesRuleText;
    protected SetTargetPointer setTargetPointer;

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription) {
        this(effect, attachedDescription, false);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional) {
        this(effect, attachedDescription, optional, true);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean diesRuleText) {
        this(effect, attachedDescription, optional, diesRuleText, SetTargetPointer.NONE);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional,
                                        boolean diesRuleText, SetTargetPointer setTargetPointer) {
        super(Zone.ALL, effect, optional); // because the trigger only triggers if the object was attached, it doesn't matter where the Attachment was moved to (e.g. by replacement effect) after the trigger triggered, so Zone.all
        this.attachedDescription = attachedDescription;
        this.diesRuleText = diesRuleText;
        this.setTargetPointer = setTargetPointer;
    }

    public DiesAttachedTriggeredAbility(final DiesAttachedTriggeredAbility ability) {
        super(ability);
        this.attachedDescription = ability.attachedDescription;
        this.diesRuleText = ability.diesRuleText;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiesAttachedTriggeredAbility copy() {
        return new DiesAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((ZoneChangeEvent) event).isDiesEvent()) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        boolean triggered = false;
        if (zEvent.getTarget() != null
                && zEvent.getTarget().getAttachments() != null
                && zEvent.getTarget().getAttachments().contains(this.getSourceId())) {
            triggered = true;
        } else {
            // If the attachment and attachedTo went to graveyard at the same time, the trigger applies.
            // If the attachment is removed beforehand, the trigger fails.
            // IE: A player cast Planar Clensing.  The attachment is Disenchanted in reponse
            // and successfully removed from the attachedTo.  The trigger fails.
            Permanent attachment = getSourcePermanentOrLKI(game);
            Card attachmentCard = game.getCard(getSourceId());
            if (attachment != null
                    && zEvent.getTargetId() != null
                    && attachment.getAttachedTo() != null
                    && zEvent.getTargetId().equals(attachment.getAttachedTo())) {
                Permanent attachedTo = game.getPermanentOrLKIBattlefield(attachment.getAttachedTo());
                if (attachedTo != null
                        && game.getState().getZone(attachedTo.getId()) == (Zone.GRAVEYARD)  // Demonic Vigor
                        && attachmentCard != null
                        && attachment.getAttachedToZoneChangeCounter() == attachedTo.getZoneChangeCounter(game)
                        && attachment.getZoneChangeCounter(game) == attachmentCard.getZoneChangeCounter(game)) {
                    triggered = true;
                }
            }
        }
        if (!triggered) {
            return false;
        }
        if (zEvent.getTarget() == null) {
            return true;
        }
        getEffects().setValue("attachedTo", zEvent.getTarget());
        getEffects().setValue("zcc", zEvent.getTarget().getZoneChangeCounter(game) + 1); // zone change info from battlefield
        if (setTargetPointer == SetTargetPointer.ATTACHED_TO_CONTROLLER) {
            Permanent attachment = game.getPermanentOrLKIBattlefield(getSourceId());
            if (attachment != null
                    && attachment.getAttachedTo() != null) {
                Permanent attachedTo = (Permanent) game.getLastKnownInformation(attachment.getAttachedTo(),
                        Zone.BATTLEFIELD, attachment.getAttachedToZoneChangeCounter());
                if (attachedTo != null) {
                    getEffects().setTargetPointer(new FixedTarget(attachedTo.getControllerId()));
                }
            }
        }
        // set targetpointer to the creature that died
        if (setTargetPointer == SetTargetPointer.CARD
                || setTargetPointer == SetTargetPointer.PERMANENT) {
            Permanent attachment = game.getPermanentOrLKIBattlefield(getSourceId());
            if (attachment != null
                    && attachment.getAttachedTo() != null) {
                Permanent attachedTo = (Permanent) game.getLastKnownInformation(attachment.getAttachedTo(),
                        Zone.BATTLEFIELD, attachment.getAttachedToZoneChangeCounter());
                if (attachedTo != null) {
                    getEffects().setTargetPointer(new FixedTarget(attachedTo.getId()));
                }
            }
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        StringBuilder sb = new StringBuilder();
        if (attachedDescription.startsWith("equipped")) {
            sb.append("Whenever ");
        } else {
            sb.append("When ");
        }
        sb.append(attachedDescription);
        if (diesRuleText) {
            sb.append(" dies, ");
        } else {
            sb.append(" is put into graveyard, ");
        }
        return sb.toString();
    }
}

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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

    private String attachedDescription;
    private boolean diesRuleText;
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

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional, boolean diesRuleText, SetTargetPointer setTargetPointer) {
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
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            boolean triggered = false;
            if (zEvent.getTarget() != null && zEvent.getTarget().getAttachments() != null && zEvent.getTarget().getAttachments().contains(this.getSourceId())) {
                triggered = true;
            } else {
                // If both (attachment and attached went to graveyard at the same time, the attachemnets can be already removed from the attached object.)
                // So check here with the LKI of the enchantment
                Permanent attachment = game.getPermanentOrLKIBattlefield(getSourceId());
                if (attachment != null
                        && zEvent.getTargetId() != null && attachment.getAttachedTo() != null
                        && zEvent.getTargetId().equals(attachment.getAttachedTo())) {
                    Permanent attachedTo = game.getPermanentOrLKIBattlefield(attachment.getAttachedTo());
                    if (attachedTo != null
                            && attachment.getAttachedToZoneChangeCounter() == attachedTo.getZoneChangeCounter(game)) {  // zoneChangeCounter is stored in Permanent
                        triggered = true;
                    }
                }
            }
            if (triggered) {
                for (Effect effect : getEffects()) {
                    if (zEvent.getTarget() != null) {
                        effect.setValue("attachedTo", zEvent.getTarget());
                        if (setTargetPointer == SetTargetPointer.ATTACHED_TO_CONTROLLER) {
                            Permanent attachment = game.getPermanentOrLKIBattlefield(getSourceId());
                            if (attachment != null && attachment.getAttachedTo() != null) {
                                Permanent attachedTo = (Permanent) game.getLastKnownInformation(attachment.getAttachedTo(), Zone.BATTLEFIELD, attachment.getAttachedToZoneChangeCounter());
                                if (attachedTo != null) {
                                    effect.setTargetPointer(new FixedTarget(attachedTo.getControllerId()));
                                }
                            }
                        }
                    }
                }
                return true;
            }

        }
        return false;
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("When ").append(attachedDescription);
        if (diesRuleText) {
            sb.append(" dies, ");
        } else {
            sb.append(" is put into graveyard, ");
        }
        sb.append(super.getRule());
        return sb.toString();
    }
}
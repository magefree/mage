package mage.abilities.common;

import mage.MageObject;
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
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 * "When enchanted/equipped creature dies" triggered ability
 *
 * @author Loki
 */
public class DiesAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final String attachedDescription;
    private final boolean diesRuleText;
    protected SetTargetPointer setTargetPointer;
    private final boolean rememberSource; // if true, setTargetPointer will be set to the aura/equipement.

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
        this(effect, attachedDescription, optional, diesRuleText, setTargetPointer, false);
    }

    public DiesAttachedTriggeredAbility(Effect effect, String attachedDescription, boolean optional,
                                        boolean diesRuleText, SetTargetPointer setTargetPointer, boolean rememberSource) {
        super(Zone.ALL, effect, optional); // because the trigger only triggers if the object was attached, it doesn't matter where the Attachment was moved to (e.g. by replacement effect) after the trigger triggered, so Zone.all
        this.attachedDescription = attachedDescription;
        this.diesRuleText = diesRuleText;
        this.setTargetPointer = setTargetPointer;
        this.rememberSource = rememberSource;
        setTriggerPhrase(generateTriggerPhrase());
        setLeavesTheBattlefieldTrigger(true);
    }

    protected DiesAttachedTriggeredAbility(final DiesAttachedTriggeredAbility ability) {
        super(ability);
        this.attachedDescription = ability.attachedDescription;
        this.diesRuleText = ability.diesRuleText;
        this.setTargetPointer = ability.setTargetPointer;
        this.rememberSource = ability.rememberSource;
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
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent creatureDied = zEvent.getTarget();
        if (creatureDied == null || !zEvent.isDiesEvent()) {
            return false;
        }
        boolean triggered = false;
        if (creatureDied.getAttachments().contains(this.getSourceId())) {
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
        getEffects().setValue("attachedTo", creatureDied);
        getEffects().setValue("zcc", creatureDied.getZoneChangeCounter(game) + 1); // zone change info from battlefield
        if (setTargetPointer == SetTargetPointer.ATTACHED_TO_CONTROLLER) {
            getEffects().setTargetPointer(new FixedTarget(creatureDied.getControllerId()));
        }
        if (setTargetPointer == SetTargetPointer.CARD) {
            if (rememberSource) {
                // set targetpointer to the attachment
                Permanent attachment = game.getPermanentOrLKIBattlefield(getSourceId());
                if (attachment != null) {
                    getEffects().setTargetPointer(new FixedTarget(attachment.getId()));
                }
            } else {
                // set targetpointer to the creature that died
                getEffects().setTargetPointer(new FixedTargets(
                        CardUtil.getAllCardsFromPermanentLeftBattlefield(creatureDied, game), game));
            }
        }
        return true;
    }

    private String generateTriggerPhrase() {
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
            sb.append(" is put into a graveyard, ");
        }
        return sb.toString();
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}

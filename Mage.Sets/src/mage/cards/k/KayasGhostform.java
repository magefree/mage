package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayasGhostform extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public KayasGhostform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or planeswalker you control
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted permanent dies or is put into exile, return that card to the battlefield under your control.
        this.addAbility(new KayasGhostformTriggeredAbility());
    }

    private KayasGhostform(final KayasGhostform card) {
        super(card);
    }

    @Override
    public KayasGhostform copy() {
        return new KayasGhostform(this);
    }
}

class KayasGhostformTriggeredAbility extends TriggeredAbilityImpl {

    KayasGhostformTriggeredAbility() {
        super(Zone.ALL, new ReturnToBattlefieldUnderYourControlAttachedEffect(), false);
    }

    private KayasGhostformTriggeredAbility(final KayasGhostformTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KayasGhostformTriggeredAbility copy() {
        return new KayasGhostformTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() != Zone.BATTLEFIELD
                || !(zEvent.getToZone() == Zone.GRAVEYARD || zEvent.getToZone() == Zone.EXILED)) {
            return false;
        }
        if (zEvent.getTarget() != null && zEvent.getTarget().getAttachments() != null
                && zEvent.getTarget().getAttachments().contains(this.getSourceId())) {
            getEffects().get(0).setValue("attachedTo", zEvent.getTarget());
            return true;
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
                    getEffects().get(0).setValue("attachedTo", attachedTo);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted permanent dies or is put into exile, " +
                "return that card to the battlefield under your control.";
    }
}

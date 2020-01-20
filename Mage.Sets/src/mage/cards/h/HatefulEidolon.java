package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public final class HatefulEidolon extends CardImpl {

    public HatefulEidolon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever an enchanted creature dies, draw a card for each 
        // Aura you controlled that was attached to it.
        this.addAbility(new HatefulEidolonTriggeredAbility());
    }

    private HatefulEidolon(final HatefulEidolon card) {
        super(card);
    }

    @Override
    public HatefulEidolon copy() {
        return new HatefulEidolon(this);
    }
}

class HatefulEidolonTriggeredAbility extends TriggeredAbilityImpl {

    HatefulEidolonTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    private HatefulEidolonTriggeredAbility(final HatefulEidolonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HatefulEidolonTriggeredAbility copy() {
        return new HatefulEidolonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int auraCount = 0;
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        Permanent deadCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (deadCreature.getAttachments().isEmpty()) {
            return false;
        }
        for (UUID auraId : deadCreature.getAttachments()) {
            Permanent aura = game.getPermanentOrLKIBattlefield(auraId);
            if (aura.getControllerId().equals(controllerId)) {
                auraCount += 1;
            }
        }
        this.getEffects().clear();
        this.addEffect(new DrawCardSourceControllerEffect(auraCount));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an enchanted creature dies, draw a card for each "
                + "Aura you controlled that was attached to it.";
    }
}

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
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

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
            Permanent attachment = game.getPermanentOrLKIBattlefield(auraId);
            if (attachment.getControllerId().equals(controllerId)
                    && attachment.isEnchantment(game)) {  // Shadowspear or any other equipment does not count
                auraCount += 1;
            }
        }
        if (auraCount == 0) {  // just equipment not aura's
            return false;
        }
        Player controller = game.getPlayer(controllerId);
        if (controller != null
                && controller.canRespond()) {
            this.getEffects().clear();
            DrawCardTargetEffect drawCard = new DrawCardTargetEffect(auraCount);
            drawCard.setTargetPointer(new FixedTarget(controllerId));
            this.addEffect(drawCard);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an enchanted creature dies, draw a card for each "
                + "Aura you controlled that was attached to it.";
    }
}

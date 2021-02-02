package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class PlanarVoid extends CardImpl {

    public PlanarVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever another card is put into a graveyard from anywhere, exile that card.
        this.addAbility(new PlanarVoidTriggeredAbility());
    }

    private PlanarVoid(final PlanarVoid card) {
        super(card);
    }

    @Override
    public PlanarVoid copy() {
        return new PlanarVoid(this);
    }
}

class PlanarVoidTriggeredAbility extends TriggeredAbilityImpl {

    PlanarVoidTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTargetEffect(), false);
    }

    private PlanarVoidTriggeredAbility(final PlanarVoidTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlanarVoidTriggeredAbility copy() {
        return new PlanarVoidTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.GRAVEYARD
                || event.getTargetId().equals(getSourceId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever another card is put into a graveyard from anywhere, exile that card.";
    }
}

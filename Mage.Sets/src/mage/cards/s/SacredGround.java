package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class SacredGround extends CardImpl {

    public SacredGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a spell or ability an opponent controls causes a land to be put into your graveyard from the battlefield, return that card to the battlefield.
        this.addAbility(new SacredGroundTriggeredAbility());
    }

    private SacredGround(final SacredGround card) {
        super(card);
    }

    @Override
    public SacredGround copy() {
        return new SacredGround(this);
    }
}

class SacredGroundTriggeredAbility extends TriggeredAbilityImpl {

    SacredGroundTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToBattlefieldUnderYourControlTargetEffect());
    }

    SacredGroundTriggeredAbility(final SacredGroundTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SacredGroundTriggeredAbility copy() {
        return new SacredGroundTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.getControllerId()).contains(game.getControllerId(event.getSourceId()))) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            if (Zone.BATTLEFIELD == zce.getFromZone() && Zone.GRAVEYARD == zce.getToZone()) {
                Permanent targetPermanent = zce.getTarget();
                if (targetPermanent.isLand(game) && targetPermanent.isControlledBy(getControllerId())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(targetPermanent.getId(), game.getState().getZoneChangeCounter(targetPermanent.getId())));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability an opponent controls causes a land to be put into your graveyard from the battlefield, return that card to the battlefield.";
    }
}

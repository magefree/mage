package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.PowerstoneToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlagstoneRefinery extends CardImpl {

    public SlagstoneRefinery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever Slagstone Refinery or another nontoken artifact you control is put into a graveyard from the battlefield or is put into exile from the battlefield, create a tapped Powerstone token.
        this.addAbility(new SlagstoneRefineryTriggeredAbility());
    }

    private SlagstoneRefinery(final SlagstoneRefinery card) {
        super(card);
    }

    @Override
    public SlagstoneRefinery copy() {
        return new SlagstoneRefinery(this);
    }
}

class SlagstoneRefineryTriggeredAbility extends TriggeredAbilityImpl {

    SlagstoneRefineryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new PowerstoneToken(), 1, true));
    }

    private SlagstoneRefineryTriggeredAbility(final SlagstoneRefineryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SlagstoneRefineryTriggeredAbility copy() {
        return new SlagstoneRefineryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

        // Filters events for battlefield => graveyard or exile => graveyard.
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && (zEvent.getToZone().match(Zone.GRAVEYARD) || zEvent.getToZone().match(Zone.EXILED))) {
            if(zEvent.getTargetId().equals(getSourceId())) return true; // {this}
            else {                                                                                  // another
                UUID targetId = zEvent.getTargetId();
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
                }

                // filters for 'another nontoken artifact you control'
                return zEvent.getTarget().isArtifact(game)                                          // artifact
                        && !(zEvent.getTarget() instanceof PermanentToken)                          // nontoken
                        && (permanent != null && permanent.getControllerId() == getControllerId()); // you control
            }
        }

        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nontoken artifact you control is put into a graveyard from the battlefield " +
                "or is put into exile from the battlefield, create a tapped Powerstone token.";
    }
}

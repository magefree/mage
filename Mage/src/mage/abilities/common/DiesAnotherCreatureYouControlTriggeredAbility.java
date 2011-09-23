package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class DiesAnotherCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl<DiesAnotherCreatureYouControlTriggeredAbility> {

    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
    }

    public DiesAnotherCreatureYouControlTriggeredAbility(DiesAnotherCreatureYouControlTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesAnotherCreatureYouControlTriggeredAbility copy() {
        return new DiesAnotherCreatureYouControlTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {

            UUID sourceId = getSourceId();
            if (game.getPermanent(sourceId) == null) {
                if (game.getLastKnownInformation(sourceId, Constants.Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();

            if (permanent != null && permanent.getCardType().contains(Constants.CardType.CREATURE) &&
                    zEvent.getToZone() == Constants.Zone.GRAVEYARD &&
                    zEvent.getFromZone() == Constants.Zone.BATTLEFIELD &&
                    permanent.getControllerId().equals(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature you control dies, " + super.getRule();
    }
}
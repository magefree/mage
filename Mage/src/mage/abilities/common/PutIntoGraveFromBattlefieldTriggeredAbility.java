package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko, loki
 */
public class PutIntoGraveFromBattlefieldTriggeredAbility extends TriggeredAbilityImpl<PutIntoGraveFromBattlefieldTriggeredAbility> {

    public PutIntoGraveFromBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PutIntoGraveFromBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Constants.Zone.ALL, effect, optional);
    }

    PutIntoGraveFromBattlefieldTriggeredAbility(PutIntoGraveFromBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PutIntoGraveFromBattlefieldTriggeredAbility copy() {
        return new PutIntoGraveFromBattlefieldTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {

            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();

            if (permanent != null &&
                    zEvent.getToZone() == Constants.Zone.GRAVEYARD &&
                    zEvent.getFromZone() == Constants.Zone.BATTLEFIELD &&
                    permanent.getId().equals(this.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} is put into a graveyard from the battlefield, " + super.getRule();
    }
}

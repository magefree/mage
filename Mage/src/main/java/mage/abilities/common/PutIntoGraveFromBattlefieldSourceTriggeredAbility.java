package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko, loki
 */
public class PutIntoGraveFromBattlefieldSourceTriggeredAbility extends TriggeredAbilityImpl {

    private boolean onlyToControllerGraveyard;

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect, boolean optional, boolean onlyToControllerGraveyard) {
        super(Zone.ALL, effect, optional);
        setLeavesTheBattlefieldTrigger(true);
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
    }

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(final PutIntoGraveFromBattlefieldSourceTriggeredAbility ability) {
        super(ability);
        this.onlyToControllerGraveyard = ability.onlyToControllerGraveyard;
    }

    @Override
    public PutIntoGraveFromBattlefieldSourceTriggeredAbility copy() {
        return new PutIntoGraveFromBattlefieldSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();
            if (permanent != null
                    && zEvent.isDiesEvent()) {
                return !onlyToControllerGraveyard || this.isControlledBy(game.getOwnerId(zEvent.getTargetId()));
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When {this} is put into " + (onlyToControllerGraveyard ? "your" : "a") + " graveyard from the battlefield, " ;
    }
}

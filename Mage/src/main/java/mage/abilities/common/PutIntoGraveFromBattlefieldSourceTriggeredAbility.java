package mage.abilities.common;

import mage.MageObject;
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

    private final boolean onlyToControllerGraveyard;

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect, boolean optional, boolean onlyToControllerGraveyard) {
        super(Zone.BATTLEFIELD, effect, optional);
        setLeavesTheBattlefieldTrigger(true);
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
        setTriggerPhrase("When {this} is put into " + (onlyToControllerGraveyard ? "your" : "a") + " graveyard from the battlefield, ");
    }

    protected PutIntoGraveFromBattlefieldSourceTriggeredAbility(final PutIntoGraveFromBattlefieldSourceTriggeredAbility ability) {
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
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent == null || !zEvent.isDiesEvent()
                || (onlyToControllerGraveyard && !this.isControlledBy(game.getOwnerId(zEvent.getTargetId())))) {
            return false;
        }
        this.getEffects().setValue("permanentWasCreature", permanent.isCreature(game));
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }
}

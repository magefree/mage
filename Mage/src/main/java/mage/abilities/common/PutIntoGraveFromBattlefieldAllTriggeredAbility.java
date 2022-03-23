
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class PutIntoGraveFromBattlefieldAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private boolean setTargetPointer;
    private boolean onlyToControllerGraveyard;

    public PutIntoGraveFromBattlefieldAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(effect, optional, filter, setTargetPointer, false);
    }

    public PutIntoGraveFromBattlefieldAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer, boolean onlyToControllerGraveyard) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setLeavesTheBattlefieldTrigger(true);
        this.filter = filter;
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
        this.setTargetPointer = setTargetPointer;
    }

    public PutIntoGraveFromBattlefieldAllTriggeredAbility(final PutIntoGraveFromBattlefieldAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyToControllerGraveyard = ability.onlyToControllerGraveyard;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (filter.match(zEvent.getTarget(), this.getControllerId(), this, game)) {
                if (onlyToControllerGraveyard && !this.isControlledBy(game.getOwnerId(zEvent.getTargetId()))) {
                    return false;
                }
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game.getObject(event.getTargetId()).getZoneChangeCounter(game)));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " is put into " + (onlyToControllerGraveyard ? "your" : "a")
                + " graveyard from the battlefield, " ;
    }

    @Override
    public PutIntoGraveFromBattlefieldAllTriggeredAbility copy() {
        return new PutIntoGraveFromBattlefieldAllTriggeredAbility(this);
    }
}

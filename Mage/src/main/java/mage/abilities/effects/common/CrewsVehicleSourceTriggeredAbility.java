package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.icon.abilities.CrewAbilityIcon;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class CrewsVehicleSourceTriggeredAbility extends TriggeredAbilityImpl {

    public CrewsVehicleSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        this.addIcon(CrewAbilityIcon.instance);
    }

    public CrewsVehicleSourceTriggeredAbility(final CrewsVehicleSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CrewsVehicleSourceTriggeredAbility copy() {
        return new CrewsVehicleSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREWED_VEHICLE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            for (Effect effect : getEffects()) {
                // set the vehicle id as target
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "When {this} crews a Vehicle, " ;
    }
}

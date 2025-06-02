package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.icon.CardIconImpl;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

public class CrewsVehicleSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean mountsAlso;
    private final boolean yourMainPhaseOnly;

    public CrewsVehicleSourceTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public CrewsVehicleSourceTriggeredAbility(Effect effect, boolean mountsAlso, boolean yourMainPhaseOnly) {
        super(Zone.BATTLEFIELD, effect, false);
        this.addIcon(CardIconImpl.ABILITY_CREW);
        this.mountsAlso = mountsAlso;
        this.yourMainPhaseOnly = yourMainPhaseOnly;
        setTriggerPhrase("Whenever {this}" + (mountsAlso ? " saddles a Mount or" : "") +
                " crews a Vehicle" + (yourMainPhaseOnly ? " during your main phase" : "") + ", ");
    }

    protected CrewsVehicleSourceTriggeredAbility(final CrewsVehicleSourceTriggeredAbility ability) {
        super(ability);
        this.mountsAlso = ability.mountsAlso;
        this.yourMainPhaseOnly = ability.yourMainPhaseOnly;
    }

    @Override
    public CrewsVehicleSourceTriggeredAbility copy() {
        return new CrewsVehicleSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREWED_VEHICLE || (mountsAlso && event.getType() == GameEvent.EventType.SADDLED_MOUNT);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (yourMainPhaseOnly && !(game.isMainPhase() && this.isControlledBy(game.getActivePlayerId()))) {
            return false;
        }
        if (event.getTargetId().equals(getSourceId())) {
            for (Effect effect : getEffects()) {
                // set the vehicle id as target
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
            }
            return true;
        }
        return false;
    }
}

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Beginning of controlled end step triggered ability
 *
 * @author Loki
 */
public class BeginningOfYourEndStepTriggeredAbility extends TriggeredAbilityImpl {
    public BeginningOfYourEndStepTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public BeginningOfYourEndStepTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        setTriggerPhrase("At the beginning of your end step, ");
    }

    public BeginningOfYourEndStepTriggeredAbility(final BeginningOfYourEndStepTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfYourEndStepTriggeredAbility copy() {
        return new BeginningOfYourEndStepTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }
}

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class GainLifeControllerTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public GainLifeControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public GainLifeControllerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public GainLifeControllerTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever you gain life, ");
    }

    private GainLifeControllerTriggeredAbility(final GainLifeControllerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public GainLifeControllerTriggeredAbility copy() {
        return new GainLifeControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            this.getEffects().setValue("gainedLife", event.getAmount());
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }
}

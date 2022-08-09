package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class TurnedFaceUpSourceTriggeredAbility extends TriggeredAbilityImpl {

    private boolean setTargetPointer;

    public TurnedFaceUpSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }
    
    public TurnedFaceUpSourceTriggeredAbility(Effect effect, boolean setTargetPointer) {
        this(effect, setTargetPointer, false);
    }

    public TurnedFaceUpSourceTriggeredAbility(Effect effect, boolean setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        // has to be set so the ability triggers if card is turn faced up
        this.setWorksFaceDown(true);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("When {this} is turned face up, ");
    }

    public TurnedFaceUpSourceTriggeredAbility(final TurnedFaceUpSourceTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public TurnedFaceUpSourceTriggeredAbility copy() {
        return new TurnedFaceUpSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            if (setTargetPointer) {
                for (Effect effect: getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                }
            }
            return true;
        }
        return false;
    }
}


package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class BecomesMonstrousSourceTriggeredAbility extends TriggeredAbilityImpl {

    private int monstrosityValue;

    public BecomesMonstrousSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When {this} becomes monstrous, ");
    }

    public BecomesMonstrousSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BecomesMonstrousSourceTriggeredAbility(final BecomesMonstrousSourceTriggeredAbility ability) {
        super(ability);
        this.monstrosityValue = ability.monstrosityValue;
    }

    @Override
    public BecomesMonstrousSourceTriggeredAbility copy() {
        return new BecomesMonstrousSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONSTROUS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            this.monstrosityValue = event.getAmount();
            return true;
        }
        return false;
    }

    public int getMonstrosityValue() {
        return monstrosityValue;
    }
}

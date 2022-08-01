
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
public class BecomesExertSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesExertSourceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("When {this} becomes exerted, ");
    }

    public BecomesExertSourceTriggeredAbility(final BecomesExertSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesExertSourceTriggeredAbility copy() {
        return new BecomesExertSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_EXERTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }
}

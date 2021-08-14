
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

    @Override
    public String getTriggerPhrase() {
        return "When {this} becomes exerted, " ;
    }
}

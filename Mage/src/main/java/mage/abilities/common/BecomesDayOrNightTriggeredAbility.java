package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 * TODO: this is just a placeholder for the actual ability
 */
public class BecomesDayOrNightTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesDayOrNightTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    private BecomesDayOrNightTriggeredAbility(final BecomesDayOrNightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public BecomesDayOrNightTriggeredAbility copy() {
        return new BecomesDayOrNightTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever day becomes night or night becomes day, ";
    }
}

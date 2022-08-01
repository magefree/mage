package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class BecomesDayOrNightTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesDayOrNightTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect);
    }

    public BecomesDayOrNightTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever day becomes night or night becomes day, ");
    }

    private BecomesDayOrNightTriggeredAbility(final BecomesDayOrNightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_DAY_NIGHT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public BecomesDayOrNightTriggeredAbility copy() {
        return new BecomesDayOrNightTriggeredAbility(this);
    }
}

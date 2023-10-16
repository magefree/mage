package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class PlaneswalkToSourceTriggeredAbility extends TriggeredAbilityImpl {

    public PlaneswalkToSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public PlaneswalkToSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
        setTriggerPhrase("When you planeswalk to {this}, ");
    }

    private PlaneswalkToSourceTriggeredAbility(final PlaneswalkToSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlaneswalkToSourceTriggeredAbility copy() {
        return new PlaneswalkToSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // TODO: implement
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // TODO: implement
        return false;
    }
}

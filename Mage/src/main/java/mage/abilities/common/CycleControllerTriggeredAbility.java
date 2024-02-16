package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;

/**
 * @author TheElk801
 */
public class CycleControllerTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean excludeSource;

    public CycleControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public CycleControllerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public CycleControllerTriggeredAbility(Effect effect, boolean optional, boolean excludeSource) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.excludeSource = excludeSource;
        setTriggerPhrase("Whenever you cycle " + (excludeSource ? "another" : "a") + " card, ");
    }

    private CycleControllerTriggeredAbility(final CycleControllerTriggeredAbility ability) {
        super(ability);
        this.excludeSource = ability.excludeSource;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getState().getStack().isEmpty()
                || !event.getPlayerId().equals(this.getControllerId())
                || (event.getSourceId().equals(this.getSourceId()) && excludeSource)) {
            return false;
        }
        StackObject item = game.getState().getStack().getFirst();
        return item instanceof StackAbility
                && item.getStackAbility() instanceof CyclingAbility;
    }

    @Override
    public CycleControllerTriggeredAbility copy() {
        return new CycleControllerTriggeredAbility(this);
    }
}

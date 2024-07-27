package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

public class ActivateAbilityTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;

    public ActivateAbilityTriggeredAbility(Effect effect, FilterStackObject filter) {
        super(Zone.BATTLEFIELD, effect, false);
        this.filter = filter;
        setTriggerPhrase("Whenever you activate " + filter.getMessage() + ", ");
    }

    private ActivateAbilityTriggeredAbility(final ActivateAbilityTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public ActivateAbilityTriggeredAbility copy() {
        return new ActivateAbilityTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
        if (stackAbility == null) {
            return false;
        }
        if (!filter.match(stackAbility, event.getPlayerId(), stackAbility, game)) {
            return false;
        }

        getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}

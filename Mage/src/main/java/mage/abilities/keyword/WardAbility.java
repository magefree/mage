package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class WardAbility extends TriggeredAbilityImpl {

    public WardAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(cost), false);
    }

    private WardAbility(final WardAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getSourceId().equals(event.getTargetId())) {
            return false;
        }
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null || !game.getOpponents(getControllerId()).contains(stackObject.getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
        return true;
    }

    @Override
    public WardAbility copy() {
        return new WardAbility(this);
    }

    @Override
    public String getRule() {
        return "Ward " + this.getCosts().getText()
                + " <i>(Whenever {this} becomes the target of a spell or ability an opponent controls, " +
                "counter that spell or ability unless its controller pays " + this.getCosts().getText() + ")</i>";
    }
}

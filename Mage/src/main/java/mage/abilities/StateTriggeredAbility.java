package mage.abilities;

import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * 603.8.
 * Some triggered abilities trigger when a game state (such as a player controlling no permanents of
 * a particular card type) is true, rather than triggering when an event occurs. These abilities trigger
 * as soon as the game state matches the condition. They'll go onto the stack at the next available opportunity.
 * These are called state triggers. (Note that state triggers aren't the same as state-based actions.)
 * A state-triggered ability doesn't trigger again until the ability has resolved, has been countered,
 * or has otherwise left the stack. Then, if the object with the ability is still in the same zone and
 * the game state still matches its trigger condition, the ability will trigger again.
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class StateTriggeredAbility extends TriggeredAbilityImpl {

    public StateTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect);
    }

    public StateTriggeredAbility(final StateTriggeredAbility ability) {
        super(ability);
    }

    public boolean canTrigger(Game game) {
        // 603.8 - A state-triggered ability doesn't trigger again until the ability has resolved,
        // has been countered, or has otherwise left the stack
        return !Boolean.TRUE.equals(game.getState().getValue(getSourceId().toString() + "triggered"));
    }

    @Override
    public final boolean checkEventType(GameEvent event, Game game) {
        return false;
    }

    @Override
    public void trigger(Game game, UUID controllerId, GameEvent triggeringEvent) {
        //20100716 - 603.8
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.TRUE);
        super.trigger(game, controllerId, triggeringEvent);
    }

    @Override
    public boolean resolve(Game game) {
        //20100716 - 603.8
        boolean result = super.resolve(game);
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
        return result;
    }

    public void counter(Game game) {
        game.getState().setValue(this.getSourceId().toString() + "triggered", Boolean.FALSE);
    }
}

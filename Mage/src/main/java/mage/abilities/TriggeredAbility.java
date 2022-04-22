
package mage.abilities;

import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public interface TriggeredAbility extends Ability {

    void trigger(Game game, UUID controllerId, GameEvent event);

    /**
     * This check for the relevant event types is called at first to prevent
     * further actions if the current event is ignored from this triggered
     * ability
     *
     * @param event
     * @param game
     * @return
     */
    boolean checkEventType(GameEvent event, Game game);

    /**
     * This method checks if the event has to trigger the ability. It's
     * important to do nothing unique within this method, that can't be done
     * multiple times. Because some abilities call this to check if an ability
     * is relevant (e.g. Torpor Orb), so the method is called multiple times for
     * the same event.
     *
     * @param event
     * @param game
     * @return
     */
    boolean checkTrigger(GameEvent event, Game game);

    boolean checkTriggeredAlready(Game game);

    boolean checkUsedAlready(Game game);

    TriggeredAbility setTriggersOnce(boolean triggersOnce);

    boolean checkInterveningIfClause(Game game);

    boolean isOptional();

    boolean isLeavesTheBattlefieldTrigger();

    void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger);

    @Override
    TriggeredAbility copy();

    void setTriggerEvent(GameEvent event);

    GameEvent getTriggerEvent();

    String getTriggerPhrase();

    TriggeredAbility setTriggerPhrase(String triggerPhrase);
}

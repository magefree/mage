
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
     */
    boolean checkEventType(GameEvent event, Game game);

    /**
     * This method checks if the event has to trigger the ability,
     * and if it does trigger, may set targets and other values in associated effects
     * before returning true.
     */
    boolean checkTrigger(GameEvent event, Game game);

    /**
     * If the trigger is limited per turn, check if it can trigger again or the limit is met.
     * true if unlimited
     */
    boolean checkTriggeredLimit(Game game);

    boolean checkUsedAlready(Game game);

    /**
     * limit the number of triggers each turn
     */
    TriggeredAbility setTriggersLimitEachTurn(int limit);

    /**
     * Get the number of times the trigger may trigger this turn.
     * e.g. 0, 1 or 2 for a trigger that is limited to trigger twice each turn.
     * Integer.MAX_VALUE when no limit.
     */
    int getRemainingTriggersLimitEachTurn(Game game);

    TriggeredAbility setDoOnlyOnceEachTurn(boolean doOnlyOnce);

    /**
     * if true, replaces "{this}" with "it" in the effect text
     */
    TriggeredAbility withRuleTextReplacement(boolean replaceRuleText);

    boolean checkInterveningIfClause(Game game);

    boolean isOptional();

    TriggeredAbility setOptional();

    boolean isLeavesTheBattlefieldTrigger();

    void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger);

    @Override
    TriggeredAbility copy();

    void setTriggerEvent(GameEvent event);

    GameEvent getTriggerEvent();

    TriggeredAbility setTriggerPhrase(String triggerPhrase);
}

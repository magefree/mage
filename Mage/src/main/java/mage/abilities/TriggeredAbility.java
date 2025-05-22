package mage.abilities;

import mage.abilities.condition.Condition;
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
     * limit the number of triggers each game
     */
    TriggeredAbility setTriggersLimitEachGame(int limit);

    /**
     * Get the number of times the trigger may trigger this turn.
     * e.g. 0, 1 or 2 for a trigger that is limited to trigger twice each turn.
     * Integer.MAX_VALUE when no limit.
     */
    int getRemainingTriggersLimitEachTurn(Game game);

    /**
     * Get the number of times the trigger may trigger this game.
     * e.g. 0, 1 or 2 for a trigger that is limited to trigger twice each game.
     * Integer.MAX_VALUE when no limit.
     */
    int getRemainingTriggersLimitEachGame(Game game);

    TriggeredAbility setDoOnlyOnceEachTurn(boolean doOnlyOnce);

    /**
     * if true, replaces "{this}" with "it" in the effect text
     */
    TriggeredAbility withRuleTextReplacement(boolean replaceRuleText);

    TriggeredAbility withInterveningIf(Condition condition);

    boolean checkInterveningIfClause(Game game);

    TriggeredAbility withTriggerCondition(Condition condition);

    Condition getTriggerCondition();

    boolean checkTriggerCondition(Game game);

    boolean isOptional();

    TriggeredAbility setOptional();

    /**
     * Allow trigger to fire after source leave the battlefield (example: will use LKI on itself sacrifice)
     */
    boolean isLeavesTheBattlefieldTrigger();

    /**
     * 603.6c,603.6d
     * If true the game “looks back in time” to determine if those abilities trigger
     * This has to be set, if the triggered ability has to check back in time if the permanent the ability is connected
     * to had the ability on the battlefield while the trigger is checked
     * <p>
     * 603.6c
     * Leaves-the-battlefield abilities trigger when a permanent moves from the battlefield to another zone,
     * or when a phased-in permanent leaves the game because its owner leaves the game. These are written as,
     * but aren’t limited to, “When [this object] leaves the battlefield, . . .” or “Whenever [something] is put
     * into a graveyard from the battlefield, . . . .” (See also rule 603.10.) An ability that attempts to do
     * something to the card that left the battlefield checks for it only in the first zone that it went to.
     * An ability that triggers when a card is put into a certain zone “from anywhere” is never treated as a
     * leaves-the-battlefield ability, even if an object is put into that zone from the battlefield.
     */
    void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger);

    @Override
    TriggeredAbility copy();

    void setTriggerEvent(GameEvent event);

    GameEvent getTriggerEvent();

    TriggeredAbility setTriggerPhrase(String triggerPhrase);

    String getTriggerPhrase();
}

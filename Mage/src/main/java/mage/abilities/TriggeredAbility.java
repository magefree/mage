
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

    boolean checkTriggeredAlready(Game game);

    boolean checkUsedAlready(Game game);

    TriggeredAbility setTriggersOnceEachTurn(boolean triggersOnce);

    boolean getTriggersOnceEachTurn();

    TriggeredAbility setDoOnlyOnceEachTurn(boolean doOnlyOnce);

    /**
     * if true, replaces "{this}" with "it" in the effect text
     */
    TriggeredAbility withRuleTextReplacement(boolean replaceRuleText);

    boolean checkInterveningIfClause(Game game);

    boolean isOptional();

    boolean isLeavesTheBattlefieldTrigger();

    void setLeavesTheBattlefieldTrigger(boolean leavesTheBattlefieldTrigger);

    @Override
    TriggeredAbility copy();

    void setTriggerEvent(GameEvent event);

    GameEvent getTriggerEvent();

    TriggeredAbility setTriggerPhrase(String triggerPhrase);
}

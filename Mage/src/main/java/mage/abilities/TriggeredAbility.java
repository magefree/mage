package mage.abilities;

import mage.abilities.condition.Condition;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Optional;
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

    /**
     * 603.4. A triggered ability may read "When/Whenever/At [trigger event], if [condition], [effect]."
     * When the trigger event occurs, the ability checks whether the stated condition is true.
     * The ability triggers only if it is; otherwise it does nothing. If the ability triggers,
     * it checks the stated condition again as it resolves. If the condition isn't true at that time,
     * the ability is removed from the stack and does nothing. Note that this mirrors the check for legal targets.
     * This rule is referred to as the "intervening 'if' clause" rule.
     * (The word "if" has only its normal English meaning anywhere else in the text of a card;
     * this rule only applies to an "if" that immediately follows a trigger condition.)
     *
     * @param condition the condition to be checked
     * @return
     */
    TriggeredAbility withInterveningIf(Condition condition);

    boolean checkInterveningIfClause(Game game);

    /**
     * Unlike intervening if, this is for a condition that's checked only on trigger and not also on resolution.
     *
     * @param condition the condition to be checked
     * @return
     */
    TriggeredAbility withTriggerCondition(Condition condition);

    Condition getTriggerCondition();

    boolean checkTriggerCondition(Game game);

    boolean isOptional();

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

    static String makeDidThisTurnString(Ability ability, Game game) {
        return CardUtil.getCardZoneString("lastTurnUsed" + ability.getOriginalId(), ability.getSourceId(), game);
    }

    static void setDidThisTurn(Ability ability, Game game) {
        game.getState().setValue(makeDidThisTurnString(ability, game), game.getTurnNum());
    }

    /**
     * For abilities which say "Do this only once each turn".
     * Most of the time this is handled automatically by calling setDoOnlyOnceEachTurn(true),
     * but sometimes the ability will need a way to clear whether it's been used this turn within an effect.
     *
     * @param ability
     * @param game
     */
    static void clearDidThisTurn(Ability ability, Game game) {
        game.getState().removeValue(makeDidThisTurnString(ability, game));
    }

    static boolean checkDidThisTurn(Ability ability, Game game) {
        return Optional
                .ofNullable(makeDidThisTurnString(ability, game))
                .map(game.getState()::getValue)
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .filter(x -> x == game.getTurnNum())
                .isPresent();
    }
}

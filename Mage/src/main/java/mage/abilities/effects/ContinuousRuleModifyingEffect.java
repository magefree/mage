package mage.abilities.effects;

import mage.abilities.Ability;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public interface ContinuousRuleModifyingEffect extends ContinuousEffect {

    /**
     * This check for the relevant events is called at first to prevent further actions if
     * the current event is ignored from this effect. Speeds up event handling.
     * @param event
     * @param game
     * @return
     */
    boolean checksEventType(GameEvent event, Game game);

    /**
     * 
     * @param event the event to check if it may happen
     * @param source the ability of the effect
     * @param game the game
     * @return 
     */
    boolean applies(GameEvent event, Ability source, Game game);
 
    /**
     * Defines if the user should get a message about the rule modifying effect
     * if it was applied
     * 
     * @return true if user should be informed
     */
    boolean sendMessageToUser();

    /**
     * Defines if the a message should be send to game log about the rule modifying effect
     * if it was applied
     * 
     * @return true if message should go to game log
     */
    boolean sendMessageToGameLog();
    /**
     * Returns a message text that informs the player why they can't do something.
     * 
     * @param source the ability of the effect
     * @param event
     * @param game the game
     * @return 
     */
    String getInfoMessage(Ability source, GameEvent event, Game game);
}

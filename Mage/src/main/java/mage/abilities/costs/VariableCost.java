

package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public interface VariableCost {
    /**
     * Returns the variable amount if already set
     * 
     * @return
     */
    int getAmount();
    /**
     * Sets the variable amount
     *
     * @param amount
     */
    void setAmount(int amount);

    /**
     * returns the action text (e.g. "creature cards to exile from your hand", "life to pay")
     *
     * @return
     */
    String getActionText();
    /**
     * Return a min value to announce
     *
     * @param source
     * @param game
     * @return
     */
    int getMinValue(Ability source, Game game);
    /**
     * Returns a max value to announce
     *
     * @param source
     * @param game
     * @return
     */
    int getMaxValue(Ability source, Game game);
    /**
     * Asks the controller to announce the variable value
     * @param source
     * @param game
     * @return
     */
    int announceXValue(Ability source, Game game);
    /**
     * Returns a fixed cost with the announced variable value
     *
     * @param xValue
     * @return
     */
    Cost getFixedCostsFromAnnouncedValue(int xValue);
}

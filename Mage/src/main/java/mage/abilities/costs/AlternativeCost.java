
package mage.abilities.costs;

import mage.game.Game;

/**
 * Virtual alternative cost, it must be tranformed to simple cost on activate in your
 * custom ability (see askToActivateAlternativeCosts). Example: DashAbility
 *
 * @author LevelX2
 */
public interface AlternativeCost extends Cost {

    String getName();

    /**
     * Returns the complete text for the alternate cost or if onlyCost is true
     * only the pure text fore the included native cost
     *
     * @param onlyCost
     * @return
     */
    String getText(boolean onlyCost);

    /**
     * Returns a reminder text, if the cost has one
     *
     * @return
     */
    String getReminderText();

    /**
     * Returns a text suffix for the game log, that can be added to
     * the cast message.
     *
     * @param position - if there are multiple costs, it's the postion the cost is set (starting with 0)
     * @return
     */
    String getCastSuffixMessage(int position);


    /**
     * If the player intends to pay the alternate cost, the cost will be activated
     */
    void activate();

    /**
     * Reset the activate
     */
    void reset();

    /**
     * Returns if the cost was activated
     *
     * @param game
     * @return
     */
    boolean isActivated(Game game);

    Cost getCost();

    @Override
    AlternativeCost copy();
}

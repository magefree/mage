package mage.abilities.costs;

import mage.util.Copyable;

/**
 * @author LevelX2
 */
public interface OptionalAdditionalCost extends Cost, Copyable<OptionalAdditionalCost> {

    String getName();

    /**
     * Returns the complete text for the addional cost or if onlyCost is true
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

    void setReminderText(String reminderText);

    void setMinimumCost(int minimumCost);

    /**
     * Returns a text suffix for the game log, that can be added to the cast
     * message.
     *
     * @param position - if there are multiple costs, it's the postion the cost
     *                 is set (starting with 0)
     * @return
     */
    String getCastSuffixMessage(int position);

    /**
     * If the player intends to pay the cost, the cost will be activated
     */
    void activate();

    /**
     * Reset the activate and count information
     */
    void reset();

    /**
     * Set if the cost be multiple times activated
     *
     * @param repeatable
     */
    void setRepeatable(boolean repeatable);

    /**
     * Can the cost be multiple times activated
     *
     * @return
     */
    boolean isRepeatable();

    /**
     * Returns if the cost was activated
     *
     * @return
     */
    boolean isActivated();

    /**
     * Returns the number of times the cost was activated
     *
     * @return
     */
    int getActivateCount();

}

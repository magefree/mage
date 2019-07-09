
package mage.abilities.costs;

import mage.game.Game;

/**
 * Alternative costs
 *
 * @author LevelX2
 *
 * @param <T>
 */
public class AlternativeCost2Impl<T extends AlternativeCost2Impl<T>> extends CostsImpl<Cost> implements AlternativeCost2 {

    protected String name;
    protected String reminderText;
    protected String delimiter;

    protected boolean activated;

    public AlternativeCost2Impl(String name, String reminderText, Cost cost) {
        this(name, " ", reminderText, cost);
    }

    public AlternativeCost2Impl(String name, String delimiter, String reminderText, Cost cost) {
        this.activated = false;
        this.name = name;
        this.delimiter = delimiter;
        if (reminderText != null) {
            this.reminderText = "<i>" + reminderText + "</i>";
        }
        this.add(cost);
    }

    public AlternativeCost2Impl(final AlternativeCost2Impl cost) {
        super(cost);
        this.name = cost.name;
        this.reminderText = cost.reminderText;
        this.activated = cost.activated;
        this.delimiter = cost.delimiter;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the complete text for the addional cost or if onlyCost is true
     * only the pure text for the included native cost
     *
     * @param onlyCost
     * @return
     */
    @Override
    public String getText(boolean onlyCost) {
        if (onlyCost) {
            return getText();
        } else {
            return (name != null ? name : "") + (delimiter != null ? delimiter : "") + getText();
        }
    }

    /**
     * Returns a reminder text, if the cost has one
     *
     * @return
     */
    @Override
    public String getReminderText() {
        String replace = "";
        if (reminderText != null && !reminderText.isEmpty()) {
            replace = reminderText.replace("{cost}", this.getText(true));
        }
        return replace;
    }

    /**
     * Returns a text suffix for the game log, that can be added to the cast
     * message.
     *
     * @param position - if there are multiple costs, it's the postion the cost
     * is set (starting with 0)
     * @return
     */
    @Override
    public String getCastSuffixMessage(int position) {
        StringBuilder sb = new StringBuilder(position > 0 ? " and " : "").append(" with ");
        sb.append(name);
        return sb.toString();
    }

    /**
     * If the player intends to pay the cost, the cost will be activated
     *
     */
    @Override
    public void activate() {
        activated = true;
    }

    /**
     * Reset the activate and count information
     *
     */
    @Override
    public void reset() {
        activated = false;
    }

    /**
     * Returns if the cost was activated
     *
     * @param game
     * @return
     */
    @Override
    public boolean isActivated(Game game) {
        return activated;
    }

    @Override
    public AlternativeCost2Impl copy() {
        return new AlternativeCost2Impl(this);
    }

    @Override
    public Cost getCost() {
        if (this.iterator().hasNext()) {
            return this.iterator().next();
        }
        return null;
    }

}

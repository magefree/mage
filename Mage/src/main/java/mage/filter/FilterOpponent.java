
package mage.filter;

import mage.constants.TargetController;

/**
 * @author LevelX2
 */
public class FilterOpponent extends FilterPlayer {

    public FilterOpponent() {
        this("opponent");
    }

    public FilterOpponent(String text) {
        super(text);
        add(TargetController.OPPONENT.getPlayerPredicate());
    }

    protected FilterOpponent(final FilterOpponent filter) {
        super(filter);

    }

    @Override
    public FilterOpponent copy() {
        return new FilterOpponent(this);
    }
}

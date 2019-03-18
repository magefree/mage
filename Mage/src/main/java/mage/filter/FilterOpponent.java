
package mage.filter;

import mage.constants.TargetController;
import mage.filter.predicate.other.PlayerPredicate;

/**
 *
 * @author LevelX2
 */
public class FilterOpponent extends FilterPlayer {

    public FilterOpponent() {
        this("opponent");
    }

    public FilterOpponent(String text) {
        super(text);
        add(new PlayerPredicate(TargetController.OPPONENT));
    }

    public FilterOpponent(final FilterOpponent filter) {
        super(filter);

    }

    @Override
    public FilterOpponent copy() {
        return new FilterOpponent(this);
    }
}

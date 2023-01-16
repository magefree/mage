package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Describes condition when source matches specified filter
 *
 * @author magenoxx_at_gmail.com
 */
public class SourceMatchesFilterCondition implements Condition {

    private final FilterPermanent FILTER;
    private final String text;

    public SourceMatchesFilterCondition(FilterPermanent filter) {
        this(null, filter);
    }

    public SourceMatchesFilterCondition(String text, FilterPermanent filter) {
        this.FILTER = filter;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return FILTER.match(permanent, permanent.getControllerId(), source, game);
    }

    @Override
    public String toString() {
        if (text != null) {
            return text;
        }
        return super.toString();
    }
}

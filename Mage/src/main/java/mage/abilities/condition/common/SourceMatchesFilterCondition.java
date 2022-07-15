
package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;

/**
 * Describes condition when source matches specified filter
 *
 * @author magenoxx_at_gmail.com
 */
public class SourceMatchesFilterCondition implements Condition {

    private FilterPermanent filter;
    private String text;

    public SourceMatchesFilterCondition(FilterPermanent filter) {
        this(null, filter);
    }

    public SourceMatchesFilterCondition(String text, FilterPermanent filter) {
        this.filter = filter;
        this.text = text;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (filter.match(permanent, permanent.getControllerId(), source, game)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        if (text != null) {
            return text;
        }
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SourceMatchesFilterCondition other = (SourceMatchesFilterCondition) obj;
        return Objects.equals(this.filter, other.filter)
                && Objects.equals(this.text, other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, text);
    }
}

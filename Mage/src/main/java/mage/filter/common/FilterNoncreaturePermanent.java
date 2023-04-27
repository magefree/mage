

package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;


/**
 * @author Merlingilb
 */
public class FilterNoncreaturePermanent extends FilterPermanent {

    public FilterNoncreaturePermanent() {
        this("noncreature permanent");
    }

    public FilterNoncreaturePermanent(String name) {
        super(name);
        this.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public FilterNoncreaturePermanent(final FilterNoncreaturePermanent filter) {
        super(filter);
    }

    @Override
    public FilterNoncreaturePermanent copy() {
        return new FilterNoncreaturePermanent(this);
    }

}

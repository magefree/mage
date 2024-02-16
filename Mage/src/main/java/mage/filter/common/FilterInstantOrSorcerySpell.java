
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 * @author duncant
 */

public class FilterInstantOrSorcerySpell extends FilterSpell {

    public FilterInstantOrSorcerySpell() {
        this("instant or sorcery spell");
    }

    public FilterInstantOrSorcerySpell(String name) {
        super(name);
        this.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    protected FilterInstantOrSorcerySpell(final FilterInstantOrSorcerySpell filter) {
        super(filter);
    }

    @Override
    public FilterInstantOrSorcerySpell copy() {
        return new FilterInstantOrSorcerySpell(this);
    }

}

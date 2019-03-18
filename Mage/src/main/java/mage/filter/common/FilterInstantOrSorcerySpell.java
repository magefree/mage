
package mage.filter.common;

import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author duncant
 */

public class FilterInstantOrSorcerySpell extends FilterSpell {

    public FilterInstantOrSorcerySpell() {
        this("instant or sorcery spell");
    }

    public FilterInstantOrSorcerySpell(String name) {
        super(name);
        this.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public FilterInstantOrSorcerySpell(final FilterInstantOrSorcerySpell filter) {
        super(filter);
    }

    @Override
    public FilterInstantOrSorcerySpell copy() {
        return new FilterInstantOrSorcerySpell(this);
    }

}

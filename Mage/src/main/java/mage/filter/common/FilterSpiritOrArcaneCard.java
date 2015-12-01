package mage.filter.common;

import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

public class FilterSpiritOrArcaneCard extends FilterSpell {

    public FilterSpiritOrArcaneCard() {
        this("a Spirit or Arcane spell");
    }

    public FilterSpiritOrArcaneCard(String name) {
        super(name);
        this.add(Predicates.or(new SubtypePredicate("Spirit"),new SubtypePredicate("Arcane")));
    }

    public FilterSpiritOrArcaneCard(final FilterSpiritOrArcaneCard filter) {
        super(filter);
    }

    @Override
    public FilterSpiritOrArcaneCard copy() {
        return new FilterSpiritOrArcaneCard(this);
    }
}

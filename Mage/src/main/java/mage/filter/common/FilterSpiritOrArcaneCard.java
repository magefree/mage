package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

public class FilterSpiritOrArcaneCard extends FilterSpell {

    public FilterSpiritOrArcaneCard() {
        this("a Spirit or Arcane spell");
    }

    public FilterSpiritOrArcaneCard(String name) {
        super(name);
        this.add(Predicates.or(SubType.SPIRIT.getPredicate(), SubType.ARCANE.getPredicate()));
    }

    protected FilterSpiritOrArcaneCard(final FilterSpiritOrArcaneCard filter) {
        super(filter);
    }

    @Override
    public FilterSpiritOrArcaneCard copy() {
        return new FilterSpiritOrArcaneCard(this);
    }
}

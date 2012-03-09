package mage.filter.common;

import mage.filter.Filter;
import mage.filter.FilterSpell;

public class FilterSpiritOrArcaneCard extends FilterSpell {

    public FilterSpiritOrArcaneCard() {
		this("a Spirit or Arcane spell");
	}

	public FilterSpiritOrArcaneCard(String name) {
		super(name);
		this.getSubtype().add("Spirit");
        this.getSubtype().add("Arcane");
        this.setScopeSubtype(Filter.ComparisonScope.Any);
	}

	public FilterSpiritOrArcaneCard(final FilterSpiritOrArcaneCard filter) {
		super(filter);
	}

	@Override
	public FilterSpiritOrArcaneCard copy() {
		return new FilterSpiritOrArcaneCard(this);
	}
}

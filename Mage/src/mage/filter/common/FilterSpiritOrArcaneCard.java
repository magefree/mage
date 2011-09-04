package mage.filter.common;

import mage.filter.Filter;
import mage.filter.FilterCard;

public class FilterSpiritOrArcaneCard  extends FilterCard<FilterSpiritOrArcaneCard> {
    private final static FilterSpiritOrArcaneCard defaultFilter = new FilterSpiritOrArcaneCard();

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

	public static FilterSpiritOrArcaneCard getDefault() {
		return defaultFilter;
	}

	@Override
	public FilterSpiritOrArcaneCard copy() {
		return new FilterSpiritOrArcaneCard(this);
	}
}

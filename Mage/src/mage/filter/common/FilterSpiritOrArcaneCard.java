package mage.filter.common;

import mage.filter.Filter;
import mage.filter.FilterCard;

public class FilterSpiritOrArcaneCard  extends FilterCard<FilterSpiritOrArcaneCard> {

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

	/**
	 * There are a lot of usages of this method, we should rip them out as we see
	 * them and replace them with <code>new FilterSpiritOrArcaneCard()</code>.  This
	 * use to return a static instance of this object which is bad as its completely
	 * mutable and leads to EXTREMELY hard to track down issues!
	 */
	@Deprecated
	public static FilterSpiritOrArcaneCard getDefault() {
		return new FilterSpiritOrArcaneCard();
	}

	@Override
	public FilterSpiritOrArcaneCard copy() {
		return new FilterSpiritOrArcaneCard(this);
	}
}

package mage.filter.common;

import mage.constants.SubType;
import mage.filter.FilterSpell;

public class FilterSubTypeSpell extends FilterSpell {

    public FilterSubTypeSpell(SubType subType, String name) {
        super(name);
        this.add(subType.getPredicate());
    }

    public FilterSubTypeSpell(SubType subType) {
        this(subType, subType + " spell");
    }

    public FilterSubTypeSpell(FilterSubTypeSpell filter) {
        super(filter);
    }

    @Override
    public FilterSpell copy() {
        return new FilterSubTypeSpell(this);
    }
}

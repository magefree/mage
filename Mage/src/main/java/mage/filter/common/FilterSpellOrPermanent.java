package mage.filter.common;

import mage.MageObject;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.MultiFilterImpl;

/**
 * @author LevelX
 */
public class FilterSpellOrPermanent extends MultiFilterImpl<MageObject> {

    public FilterSpellOrPermanent() {
        this("spell or permanent");
    }

    public FilterSpellOrPermanent(String name) {
        super(name, new FilterPermanent(), new FilterSpell());
    }

    protected FilterSpellOrPermanent(final FilterSpellOrPermanent filter) {
        super(filter);
    }

    @Override
    public FilterSpellOrPermanent copy() {
        return new FilterSpellOrPermanent(this);
    }

    public FilterPermanent getPermanentFilter() {
        return (FilterPermanent) this.innerFilters.get(0);
    }

    public FilterSpell getSpellFilter() {
        return (FilterSpell) this.innerFilters.get(1);
    }
}

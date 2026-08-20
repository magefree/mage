package mage.filter.common;

import mage.MageItem;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.MultiFilterImpl;

/**
 * @author nantuko
 */
public class FilterPermanentOrPlayer extends MultiFilterImpl<MageItem> {

    public FilterPermanentOrPlayer() {
        this("player or permanent");
    }

    public FilterPermanentOrPlayer(String name) {
        this(name, new FilterPermanent(), new FilterPlayer());
    }

    public FilterPermanentOrPlayer(String name, FilterPermanent permanentFilter, FilterPlayer playerFilter) {
        super(name, permanentFilter, playerFilter);
    }

    protected FilterPermanentOrPlayer(final FilterPermanentOrPlayer filter) {
        super(filter);
    }

    @Override
    public FilterPermanentOrPlayer copy() {
        return new FilterPermanentOrPlayer(this);
    }

    public FilterPermanent getPermanentFilter() {
        return (FilterPermanent) this.innerFilters.get(0);
    }

    public FilterPlayer getPlayerFilter() {
        return (FilterPlayer) this.innerFilters.get(1);
    }

}

package mage.filter.common;

import mage.filter.FilterPlayer;

/**
 * @author LevelX2
 */
public class FilterPlayerOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterPlayerOrPlaneswalker() {
        this("player or planeswalker");
    }

    public FilterPlayerOrPlaneswalker(String name) {
        super(name, new FilterPlaneswalkerPermanent(), new FilterPlayer());
    }

    protected FilterPlayerOrPlaneswalker(final FilterPlayerOrPlaneswalker filter) {
        super(filter);
    }

    @Override
    public FilterPlayerOrPlaneswalker copy() {
        return new FilterPlayerOrPlaneswalker(this);
    }
}

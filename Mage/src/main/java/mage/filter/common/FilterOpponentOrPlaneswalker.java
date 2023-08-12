package mage.filter.common;

import mage.filter.FilterOpponent;

/**
 * @author LevelX2
 */
public class FilterOpponentOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterOpponentOrPlaneswalker() {
        this("opponent or planeswalker");
    }

    public FilterOpponentOrPlaneswalker(String name) {
        super(name, new FilterPlaneswalkerPermanent(), new FilterOpponent());
    }

    protected FilterOpponentOrPlaneswalker(final FilterOpponentOrPlaneswalker filter) {
        super(filter);
    }

    @Override
    public FilterOpponentOrPlaneswalker copy() {
        return new FilterOpponentOrPlaneswalker(this);
    }
}

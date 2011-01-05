package mage.filters;

import mage.filters.impl.HueFilter;

/**
 * Creates filter instances.
 *
 * @author nantuko
 */
public class FilterFactory {

    private static final HueFilter hueFilter = new HueFilter();

    public static HueFilter getHueFilter() {
        return hueFilter;
    }
}

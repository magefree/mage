package mage.filters;

import mage.filters.impl.HueFilter;

/**
 * Creates filter instances.
 *
 * @author nantuko
 */
public class FilterFactory {

    public static HueFilter getHueFilter() {
        return new HueFilter();
    }
}

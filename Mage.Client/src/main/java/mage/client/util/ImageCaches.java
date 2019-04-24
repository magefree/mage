
package mage.client.util;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author draxdyn
 */
public final class ImageCaches {

    private final static ArrayList<Map> IMAGE_CACHES;

    static {
        IMAGE_CACHES = new ArrayList<>();
    }

    public static Map register(Map map) {
        IMAGE_CACHES.add(map);
        return map;
    }

    public static void flush() {
        for (Map map : IMAGE_CACHES) {
            map.clear();
        }
    }
}

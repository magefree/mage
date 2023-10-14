
package mage.client.util;

import java.util.ArrayList;

import com.google.common.cache.Cache;

/**
 *
 * @author draxdyn
 */
public final class ImageCaches {

    private static final ArrayList<Cache<?, ?>> IMAGE_CACHES;

    static {
        IMAGE_CACHES = new ArrayList<>();
    }

    public static <C extends Cache<K, V>, K, V> C register(C map) {
        IMAGE_CACHES.add(map);
        return map;
    }

    public static void flush() {
        for (Cache<?, ?> map : IMAGE_CACHES) {
            map.invalidateAll();
        }
    }
}

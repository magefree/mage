package mage.client.util;

import com.google.common.cache.Cache;

import java.util.ArrayList;

/**
 * GUI: collect info about all used image caches, so it can be cleared from a single place
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

    /**
     * Global method to clear all images cache.
     * Warning, GUI must be refreshed too for card updates, so use GUISizeHelper.refreshGUIAndCards instead
     */
    public static void clearAll() {
        for (Cache<?, ?> map : IMAGE_CACHES) {
            map.invalidateAll();
        }
    }
}

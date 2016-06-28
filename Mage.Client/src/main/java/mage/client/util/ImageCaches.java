/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import java.util.Map;
import java.util.Vector;

/**
 *
 * @author user
 */
public class ImageCaches {
    private static Vector<Map> IMAGE_CACHES;

    static {
        IMAGE_CACHES = new Vector<Map>();
    }

    public static Map register(Map map)
    {
        IMAGE_CACHES.add(map);
        return map;
    }

    public static void flush()
    {
        for (Map map : IMAGE_CACHES) {
            map.clear();
        }
    }
}

package mage.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  Cache model
 *
 *  @author noxx
 */
public class Cache implements Serializable {
    
    private int version;
    private String name;
    private Map<String, Object> cacheObjects = new HashMap<String, Object>();
   
    public Cache(String name, int version) {
        this.name = name;
        this.version = version;
    }
    
    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getCacheObjects() {
        return cacheObjects;
    }

    private static final long serialVersionUID = 1L;
}

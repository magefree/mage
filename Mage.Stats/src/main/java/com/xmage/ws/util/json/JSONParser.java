package com.xmage.ws.util.json;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Enhances working with json.
 *
 * @author noxx
 */
public class JSONParser {

    public enum CachePolicy {
        CACHE_ONE_LEVEL_ONLY,
        CACHE_ALL_LEVELS
    }

    private static final Map<String, Integer> extendedIndexes = new HashMap<String, Integer>() {{
        put("$first", 0);
        put("$second", 1);
        put("$third", 2);
        put("$fourth", 3);
        put("$fifth", 4);
    }};

    private String json;
    private JSONObject root;
    private boolean hitCache;

    private CachePolicy cachePolicy = CachePolicy.CACHE_ONE_LEVEL_ONLY;

    private Map<String, Object> cache = new HashMap<>();

    public void parseJSON(String jsonString) throws JSONValidationException {
        parseJSON(jsonString, true);
    }

    public void parseJSON(String jsonString, boolean validate) throws JSONValidationException {
        this.json = jsonString;
        prepare();
        if (validate) {
            validate();
        }
    }

    public Object get(String path) {
        return getObject(path);
    }

    public int getInt(String path) {
        return (Integer)getObject(path);
    }

    public int getIntSafe(String path) {
        if (getObject(path) == null) {
            return 0;
        }
        return (Integer)getObject(path);
    }

    public String getString(String path) {
        return (String)getObject(path);
    }

    public JSONObject getJSON(String path) {
        return (JSONObject)getObject(path);
    }

    private Object getObject(String path) {
        this.hitCache = false;
        if (cache.containsKey(path)) {
            this.hitCache = true;
            return cache.get(path);
        }
        String[] params = path.split("\\.");
        JSONObject json = this.root;
        JSONArray jsonArray = null;
        String currentPath = "";
        for (int i = 0; i < params.length - 1; i++) {
            String param = params[i];
            if (cachePolicy == CachePolicy.CACHE_ALL_LEVELS) {
                if (!currentPath.isEmpty()) {
                    currentPath += ".";
                }
                currentPath += param;
            }
            if (param.startsWith("$")) {
                if (jsonArray == null) {
                    throw new JSONOperationErrorException("Not illegal syntax at this place: " + param);
                }
                int index = getIndex(param);
                json = (JSONObject) jsonArray.get(index);
                jsonArray = null;
            } else if (param.contains("[")) {
                int find = param.indexOf('[');
                String newParam = param.substring(0, find);
                String s = param.substring(find+1, param.indexOf(']'));
                if (s.isEmpty()) {
                    jsonArray = (JSONArray) json.get(newParam);
                    json = null;
                } else {
                    int index = Integer.parseInt(s);
                    json = (JSONObject)((JSONArray) json.get(newParam)).get(index);
                    jsonArray = null;
                }
            } else {
                Object obj = json.get(param);
                if (obj instanceof JSONObject) {
                    json = (JSONObject) obj;
                    jsonArray = null;
                } else if (obj instanceof JSONArray) {
                    jsonArray = (JSONArray) obj;
                    json = null;
                } else if (obj == null) {
                    throw new IllegalStateException("json object is null");
                } else {
                    throw new IllegalStateException("json object ('"+param+"') has wrong type: " + obj.getClass());
                }

            }
            if (cachePolicy == CachePolicy.CACHE_ALL_LEVELS) {
                saveToCache(currentPath, json);
            }
        }
        String name = params[params.length - 1];

        Object value;
        if (name.startsWith("$")) {
            if (jsonArray == null) {
                throw new JSONOperationErrorException("Not illegal syntax at this place: " + name);
            }
            int index = getIndex(name);
            value = jsonArray.get(index);
        } else {
            value = json.get(name);
        }

        saveToCache(path, value);

        return value;
    }

    private int getIndex(String extendedIndex) {
        if (extendedIndexes.containsKey(extendedIndex)) {
            return extendedIndexes.get(extendedIndex);
        } else {
            throw new JSONOperationErrorException("Can't parse extended index: " + extendedIndex);
        }
    }

    private void saveToCache(String path, Object value) {
        cache.put(path, value);
    }

    public JSONArray getJSONArray(String path) {
        return (JSONArray)getObject(path);
    }

    private void prepare() {
        reset();
        if (this.json != null) {
            this.json = this.json.trim();
        }
    }

    private void validate() throws JSONValidationException {
        if (this.json == null) {
            throw new JSONValidationException("JSON is null");
        }
        try {
            this.root = (JSONObject) JSONValue.parse(this.json);
            if (this.root == null) {
                throw new JSONValidationException("Root json is null");
            }
        } catch (Exception e) {
            throw new JSONValidationException("JSON is not valid", e);
        }
    }

    public void reset() {
        this.hitCache = false;
        this.cachePolicy = CachePolicy.CACHE_ONE_LEVEL_ONLY;
        this.cache.clear();
    }

    public boolean isHitCache() {
        return hitCache;
    }

    public void setCachePolicy(CachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }
}

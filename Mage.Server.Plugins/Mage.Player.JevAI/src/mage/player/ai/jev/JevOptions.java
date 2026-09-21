package mage.player.ai.jev;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds the option map of a choice question and maps the answer back to the
 * game object it stands for. Keys are what the model sees and returns, so they
 * stay readable; duplicates get a numeric suffix.
 */
public class JevOptions<T> {

    private static final int MAX_KEY_LENGTH = 70;

    private final Map<String, String> criteria = new LinkedHashMap<>();
    private final Map<String, T> values = new LinkedHashMap<>();

    /**
     * @return the key the model must answer with to pick this value
     */
    public String add(String label, String description, T value) {
        String key = uniqueKey(label);
        criteria.put(key, description == null || description.isEmpty() ? key : description);
        values.put(key, value);
        return key;
    }

    private String uniqueKey(String label) {
        String base = label == null ? "" : label.replaceAll("\\s+", " ").trim();
        if (base.isEmpty()) {
            base = "option";
        }
        if (base.length() > MAX_KEY_LENGTH) {
            base = base.substring(0, MAX_KEY_LENGTH);
        }
        if (!criteria.containsKey(base)) {
            return base;
        }
        for (int i = 2; ; i++) {
            String key = base + " #" + i;
            if (!criteria.containsKey(key)) {
                return key;
            }
        }
    }

    public Map<String, String> criteria() {
        return criteria;
    }

    public boolean contains(String key) {
        return key != null && values.containsKey(key);
    }

    public T get(String key) {
        return key == null ? null : values.get(key);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public int size() {
        return values.size();
    }
}

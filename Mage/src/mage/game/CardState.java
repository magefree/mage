package mage.game;

import java.util.HashMap;
import java.util.Map;
import mage.counters.Counters;
/**
 *
 * @author BetaSteward
 */
public class CardState {
    
    protected Map<String, String> info;
    protected Counters counters;
    
    private static final Map<String, String> emptyInfo = new HashMap<>();
    
    public CardState() {
        counters = new Counters();
    }
    
    public CardState(final CardState state) {
        if (state.info != null) {
            info = new HashMap<>();
            info.putAll(state.info);
        }
        counters = state.counters.copy();
    }

    public CardState copy() {
        return new CardState(this);
    }
    
    public Counters getCounters() {
        return counters;
    }

    public void addInfo(String key, String value) {
        if (info == null) {
            info = new HashMap<>();
        }
        if (value == null || value.isEmpty()) {
            info.remove(key);
        } else {
            info.put(key, value);
        }
    }
    
    public Map<String, String> getInfo() {
        if (info == null) {
            return emptyInfo;
        }
        return info;
    }
    
    
    public void clear() {
        counters.clear();
        info = null;
    }
    
}

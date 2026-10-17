package mage.client.util.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

/** Optional test JVM factory, keeping GUI smoke tests out of a developer's saved settings. */
public class GuiTestPreferencesFactory implements PreferencesFactory {
    private final Preferences root = new MemoryPreferences(null, "");

    @Override
    public Preferences systemRoot() {
        return root;
    }

    @Override
    public Preferences userRoot() {
        return root;
    }

    private static class MemoryPreferences extends AbstractPreferences {
        private final Map<String, String> values = new HashMap<>();
        private final Map<String, MemoryPreferences> children = new HashMap<>();

        MemoryPreferences(AbstractPreferences parent, String name) {
            super(parent, name);
        }

        protected void putSpi(String key, String value) { values.put(key, value); }
        protected String getSpi(String key) { return values.get(key); }
        protected void removeSpi(String key) { values.remove(key); }
        protected void removeNodeSpi() { values.clear(); }
        protected String[] keysSpi() { return values.keySet().toArray(new String[0]); }
        protected String[] childrenNamesSpi() { return children.keySet().toArray(new String[0]); }
        protected AbstractPreferences childSpi(String name) {
            return children.computeIfAbsent(name, key -> new MemoryPreferences(this, key));
        }
        protected void syncSpi() { }
        protected void flushSpi() { }
    }
}

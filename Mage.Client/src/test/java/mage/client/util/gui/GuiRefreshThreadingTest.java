package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;

import static org.junit.Assert.*;

/** Independent of the GUI-scaling test fixtures and safe for a developer's saved preferences. */
public class GuiRefreshThreadingTest {
    private Object originalPreferences;
    private Map<String, String> originalCache;
    private Object originalTitleFont;
    private Object originalTooltipFont;
    private final Map<Field, Object> originalSizes = new HashMap<>();

    @Before
    public void isolateSettings() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                originalPreferences = field(MageFrame.class, "PREFS").get(null);
                originalCache = new HashMap<>(cache());
                originalTitleFont = UIManager.get("InternalFrame.titleFont");
                originalTooltipFont = UIManager.get("ToolTip.font");
                for (Field size : GUISizeHelper.class.getDeclaredFields()) {
                    if (Modifier.isStatic(size.getModifiers()) && !Modifier.isFinal(size.getModifiers())) {
                        size.setAccessible(true);
                        originalSizes.put(size, size.get(null));
                    }
                }
                field(MageFrame.class, "PREFS").set(null, new MemoryPreferences(null, ""));
                cache().clear();
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "14");
                GUISizeHelper.calculateGUISizes();
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "34");
            } catch (Exception error) {
                throw new AssertionError(error);
            }
        });
    }

    @After
    public void restoreSettings() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                field(MageFrame.class, "PREFS").set(null, originalPreferences);
                cache().clear();
                cache().putAll(originalCache);
                for (Map.Entry<Field, Object> size : originalSizes.entrySet()) {
                    size.getKey().set(null, size.getValue());
                }
                UIManager.put("InternalFrame.titleFont", originalTitleFont);
                UIManager.put("ToolTip.font", originalTooltipFont);
            } catch (Exception error) {
                throw new AssertionError(error);
            }
        });
    }

    @Test
    public void workerRefreshWaitsForTheEventDispatchThread() throws Exception {
        AtomicInteger updates = new AtomicInteger();
        AtomicBoolean offEdt = new AtomicBoolean();
        AtomicReference<Throwable> failure = new AtomicReference<>();
        PropertyChangeListener listener = event -> {
            if ("InternalFrame.titleFont".equals(event.getPropertyName())) {
                updates.incrementAndGet();
                offEdt.set(!SwingUtilities.isEventDispatchThread());
            }
        };
        UIDefaults defaults = UIManager.getDefaults();
        defaults.addPropertyChangeListener(listener);
        try {
            SwingUtilities.invokeAndWait(() -> {
                Thread downloader = new Thread(() -> {
                    try {
                        GUISizeHelper.refreshGUIAndCards(false);
                    } catch (Throwable error) {
                        failure.set(error);
                    }
                }, "test-image-downloader");
                downloader.start();
                try {
                    downloader.join(5000);
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(error);
                }
                assertFalse("The worker queues the refresh without waiting for Swing", downloader.isAlive());
                assertNull(failure.get());
                assertEquals("No Swing defaults change while the EDT is busy", 0, updates.get());
            });
            SwingUtilities.invokeAndWait(() -> {
                assertTrue("The queued refresh ran", updates.get() > 0);
                assertFalse("Swing defaults change on the EDT", offEdt.get());
                assertEquals(34, GUISizeHelper.dialogFont.getSize());
            });
        } finally {
            defaults.removePropertyChangeListener(listener);
        }
    }

    @Test
    public void eventDispatchThreadRefreshRemainsSynchronous() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            assertEquals(14, GUISizeHelper.dialogFont.getSize());
            GUISizeHelper.refreshGUIAndCards(false);
            assertEquals("An EDT caller observes the refresh immediately", 34, GUISizeHelper.dialogFont.getSize());
        });
    }

    private static Field field(Class<?> type, String name) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> cache() throws Exception {
        return (Map<String, String>) field(PreferencesDialog.class, "CACHE").get(null);
    }

    /** Optional JVM factory for running the complete client suite without using the registry. */
    public static class TestPreferencesFactory implements PreferencesFactory {
        private final Preferences root = new MemoryPreferences(null, "");
        public Preferences systemRoot() { return root; }
        public Preferences userRoot() { return root; }
    }

    private static class MemoryPreferences extends AbstractPreferences {
        private final Map<String, String> values = new HashMap<>();
        private final Map<String, MemoryPreferences> children = new HashMap<>();
        MemoryPreferences(AbstractPreferences parent, String name) { super(parent, name); }
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

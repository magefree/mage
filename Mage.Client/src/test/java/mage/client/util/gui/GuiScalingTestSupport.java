package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.*;

import javax.swing.*;
import javax.swing.plaf.InputMapUIResource;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public abstract class GuiScalingTestSupport {
    private Object originalPreferences;
    private Object originalDesktop;
    private Map<String, String> originalCache;
    private final Map<Field, Object> originalSizes = new HashMap<>();
    private LookAndFeel originalLookAndFeel;
    private Map<Object, Object> originalFontDefaults;
    private Map<Object, Object> originalLookAndFeelFonts;
    private InputMap originalSplitPaneBindings;

    @Before
    public void isolatePreferences() throws Exception {
        onEdt(() -> {
            originalLookAndFeel = UIManager.getLookAndFeel();
            originalFontDefaults = fontDefaults(UIManager.getDefaults());
            originalLookAndFeelFonts = fontDefaults(UIManager.getLookAndFeelDefaults());
            Object bindings = UIManager.get("SplitPane.ancestorInputMap");
            if (bindings instanceof InputMap) {
                originalSplitPaneBindings = new InputMapUIResource();
                KeyStroke[] keys = ((InputMap) bindings).allKeys();
                if (keys != null) {
                    for (KeyStroke key : keys) {
                        originalSplitPaneBindings.put(key, ((InputMap) bindings).get(key));
                    }
                }
            }
        });
        for (Field size : GUISizeHelper.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(size.getModifiers()) && !java.lang.reflect.Modifier.isFinal(size.getModifiers())) {
                size.setAccessible(true);
                originalSizes.put(size, size.get(null));
            }
        }
        originalPreferences = field(MageFrame.class, "PREFS").get(null);
        originalDesktop = field(MageFrame.class, "desktopPane").get(null);
        field(MageFrame.class, "PREFS").set(null, new GuiTestPreferencesFactory().userRoot());
        Map<String, String> cache = cache();
        originalCache = new HashMap<>(cache);
        cache.clear();
    }

    @After
    public void restorePreferences() throws Exception {
        field(MageFrame.class, "PREFS").set(null, originalPreferences);
        field(MageFrame.class, "desktopPane").set(null, originalDesktop);
        cache().clear();
        cache().putAll(originalCache);
        for (Map.Entry<Field, Object> size : originalSizes.entrySet()) {
            size.getKey().set(null, size.getValue());
        }
        onEdt(() -> {
            restoreFontDefaults(UIManager.getDefaults(), originalFontDefaults);
            restoreFontDefaults(originalLookAndFeel.getDefaults(), originalLookAndFeelFonts);
            try {
                UIManager.setLookAndFeel(originalLookAndFeel);
            } catch (UnsupportedLookAndFeelException error) {
                throw new IllegalStateException("Unable to restore the test look and feel", error);
            }
            UIManager.put("SplitPane.ancestorInputMap", originalSplitPaneBindings);
        });
    }

    private static Map<Object, Object> fontDefaults(UIDefaults defaults) {
        Map<Object, Object> fonts = new HashMap<>();
        for (Object key : new HashSet<>(defaults.keySet())) {
            if (key instanceof String && ((String) key).toLowerCase(Locale.ROOT).endsWith("font")) {
                fonts.put(key, defaults.get(key));
            }
        }
        return fonts;
    }

    private static void restoreFontDefaults(UIDefaults defaults, Map<Object, Object> fonts) {
        for (Object key : fontDefaults(defaults).keySet()) {
            defaults.put(key, null);
        }
        fonts.forEach(defaults::put);
    }

    private static void onEdt(Runnable task) throws Exception {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeAndWait(task);
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> cache() throws Exception {
        return (Map<String, String>) field(PreferencesDialog.class, "CACHE").get(null);
    }

    protected static Field field(Class<?> type, String name) throws Exception {
        Field field = type.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }


    protected static void layoutTree(Container root) {
        root.doLayout();
        for (Component child : root.getComponents()) {
            if (child instanceof Container) {
                layoutTree((Container) child);
            }
        }
    }

    protected static void assertButtonSizes(Container root) {
        for (Component child : root.getComponents()) {
            if (!child.isVisible()) continue;
            if (child instanceof JButton && ((JButton) child).getText() != null && !((JButton) child).getText().isEmpty()) {
                assertTrue(((JButton) child).getText() + " width", child.getWidth() >= child.getPreferredSize().width);
                assertTrue(((JButton) child).getText() + " height", child.getHeight() >= child.getPreferredSize().height);
            }
            if (child instanceof Container) assertButtonSizes((Container) child);
        }
    }
}

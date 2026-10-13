package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public abstract class GuiScalingTestSupport {
    private Object originalPreferences;
    private Object originalDesktop;
    private Map<String, String> originalCache;
    private final Map<Field, Object> originalSizes = new HashMap<>();

    @Before
    public void isolatePreferences() throws Exception {
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

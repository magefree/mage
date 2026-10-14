package mage.client.dialog;

import mage.client.util.GUISizeHelper;
import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.GuiFontDefaults;
import mage.client.util.gui.GuiScalingTestSupport;
import org.junit.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class PreferencesPlacementTest extends GuiScalingTestSupport {
    @Test
    public void cachedNativePeerIsRecreatedWhenOwnerChangesDisplays() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        Assume.assumeTrue("Requires multiple native displays", devices.length > 1);
        GraphicsConfiguration primary = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().getDefaultConfiguration();
        JFrame[] owner = new JFrame[1];
        PreferencesDialog[] original = new PreferencesDialog[1];
        SwingUtilities.invokeAndWait(() -> {
            try {
                original[0] = (PreferencesDialog) field(PreferencesDialog.class, "instance").get(null);
                field(PreferencesDialog.class, "instance").set(null, null);
                owner[0] = new JFrame(primary);
                owner[0].pack();
                owner[0].setBounds(primary.getBounds().x + 30, primary.getBounds().y + 30, 500, 400);
                PreferencesDialog.getInstance(owner[0]).pack();
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
        try {
            java.util.List<GraphicsConfiguration> displays = new java.util.ArrayList<>();
            for (GraphicsDevice device : devices) {
                if (device != primary.getDevice()) displays.add(device.getDefaultConfiguration());
            }
            displays.add(primary);
            for (GraphicsConfiguration display : displays) {
                SwingUtilities.invokeAndWait(() -> owner[0].setLocation(display.getBounds().x + 30, display.getBounds().y + 30));
                awaitDisplay(owner[0], display);
                PreferencesDialog[] current = new PreferencesDialog[1];
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        PreferencesDialog previous = (PreferencesDialog) field(PreferencesDialog.class, "instance").get(null);
                        current[0] = PreferencesDialog.getInstance(owner[0]);
                        assertNotSame("A hidden peer on the previous display is replaced", previous, current[0]);
                        assertFalse("The old peer is released", previous.isDisplayable());
                        GuiFontDefaults.apply(current[0].getContentPane());
                        current[0].pack();
                        GuiDisplayUtil.positionWindowRelativeToOwner(current[0]);
                        assertSame(current[0], PreferencesDialog.getInstance(owner[0]));
                    } catch (Exception error) {
                        throw new RuntimeException(error);
                    }
                });
                awaitDisplay(current[0], display);
                SwingUtilities.invokeAndWait(() -> {
                    assertTrue("Native dialog bounds remain on the owner's display",
                            GuiDisplayUtil.getUsableScreenBounds(display).contains(current[0].getBounds()));
                    assertFalse(owner[0].isVisible());
                    assertFalse(current[0].isVisible());
                });
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    PreferencesDialog current = (PreferencesDialog) field(PreferencesDialog.class, "instance").get(null);
                    if (current != null && current != original[0]) current.dispose();
                    field(PreferencesDialog.class, "instance").set(null, original[0]);
                } catch (Exception error) {
                    throw new RuntimeException(error);
                } finally {
                    owner[0].dispose();
                }
            });
        }
    }

    private static void awaitDisplay(Window window, GraphicsConfiguration display) throws Exception {
        boolean[] matches = {false};
        long deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(3);
        do {
            SwingUtilities.invokeAndWait(() -> matches[0] = window.getGraphicsConfiguration() == display);
            if (matches[0]) return;
            Thread.sleep(25);
        } while (System.nanoTime() < deadline);
        fail("Native peer did not reach display " + display.getDevice().getIDstring());
    }

    @Test
    public void sizeAndThemeControlsFitConfiguredFonts() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            JFrame owner = new JFrame();
            PreferencesDialog dialog = null;
            try {
                for (int font : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(font));
                    GUISizeHelper.calculateGUISizes();
                    dialog = new PreferencesDialog(owner, false);
                    GuiFontDefaults.apply(dialog.getContentPane());
                    ((JTabbedPane) field(PreferencesDialog.class, "tabsPanel").get(dialog)).setSelectedIndex(2);
                    dialog.pack();
                    layoutTree(dialog);
                    JPanel settings = (JPanel) field(PreferencesDialog.class, "panelSizeDetailedSettings").get(dialog);
                    for (Component row : settings.getComponents()) {
                        if (!(row instanceof JPanel)) continue;
                        for (Component control : ((JPanel) row).getComponents()) {
                            if (!control.isVisible()) continue;
                            assertTrue("Size control fits its row at font " + font + ": " + control.getBounds(),
                                    control.getY() >= 0 && control.getY() + control.getHeight() <= row.getHeight());
                            assertTrue("Size control has its natural height at font " + font,
                                    control.getHeight() >= control.getPreferredSize().height);
                        }
                    }
                    ((JTabbedPane) field(PreferencesDialog.class, "tabsPanel").get(dialog)).setSelectedIndex(3);
                    layoutTree(dialog);
                    JLabel themeLabel = (JLabel) field(PreferencesDialog.class, "lbSelectLabel").get(dialog);
                    FontMetrics metrics = themeLabel.getFontMetrics(themeLabel.getFont());
                    assertTrue("Complete theme label fits at font " + font,
                            themeLabel.getWidth() >= metrics.stringWidth(themeLabel.getText()));
                    assertTrue("Theme label has a complete text line at font " + font,
                            themeLabel.getHeight() >= metrics.getHeight());
                    assertFalse(dialog.isVisible());
                    dialog.dispose();
                    dialog = null;
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            } finally {
                if (dialog != null) dialog.dispose();
                owner.dispose();
            }
        });
    }

    @Test
    public void cachedDialogAttachesToClientOwnerAfterEarlyCreation() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            JFrame owner = new JFrame();
            PreferencesDialog original = null;
            try {
                original = (PreferencesDialog) field(PreferencesDialog.class, "instance").get(null);
                field(PreferencesDialog.class, "instance").set(null, null);
                PreferencesDialog early = PreferencesDialog.getInstance((Frame) null);
                PreferencesDialog owned = PreferencesDialog.getInstance(owner);
                assertSame("Preferences belongs to the supplied client frame", owner, owned.getOwner());
                assertNotSame("An early ownerless instance must be replaced", early, owned);
                assertFalse("The old dialog peer is released", early.isDisplayable());
                assertSame("Repeated opening reuses the owned dialog", owned, PreferencesDialog.getInstance(owner));
                assertFalse("The test never opens a native window", owned.isVisible());
            } catch (Exception error) {
                throw new RuntimeException(error);
            } finally {
                try {
                    PreferencesDialog current = (PreferencesDialog) field(PreferencesDialog.class, "instance").get(null);
                    if (current != null && current != original) current.dispose();
                    field(PreferencesDialog.class, "instance").set(null, original);
                } catch (Exception error) {
                    throw new RuntimeException(error);
                } finally {
                    owner.dispose();
                }
            }
        });
    }

    @Test
    public void reopenedDialogUsesOwnersCurrentDisplayDespiteStaleDialogConfiguration() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            GraphicsConfiguration primary = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();
            MovingOwner owner = new MovingOwner();
            StaleDisplayDialog dialog = new StaleDisplayDialog(owner, primary);
            try {
                for (Dimension size : new Dimension[]{new Dimension(500, 400), new Dimension(3000, 2200)}) {
                    for (GraphicsDevice device : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                        owner.currentDisplay = device.getDefaultConfiguration();
                        assertPlacement(dialog, owner.currentDisplay, size);
                    }
                    owner.currentDisplay = primary;
                    assertPlacement(dialog, primary, size);
                }
                assertFalse(owner.isVisible());
                assertFalse(dialog.isVisible());
            } finally {
                dialog.dispose();
                owner.dispose();
            }
        });
    }

    private static void assertPlacement(Window dialog, GraphicsConfiguration display, Dimension size) {
        dialog.setSize(size);
        GuiDisplayUtil.positionWindowRelativeToOwner(dialog);
        Rectangle available = GuiDisplayUtil.getUsableScreenBounds(display);
        assertTrue("Dialog remains on owner's display " + available + ": " + dialog.getBounds(),
                available.contains(dialog.getBounds()));
    }

    private static final class MovingOwner extends JFrame {
        private GraphicsConfiguration currentDisplay;

        @Override
        public GraphicsConfiguration getGraphicsConfiguration() {
            return currentDisplay == null ? super.getGraphicsConfiguration() : currentDisplay;
        }
    }

    private static final class StaleDisplayDialog extends JDialog {
        private GraphicsConfiguration originalDisplay;

        private StaleDisplayDialog(Frame owner, GraphicsConfiguration display) {
            super(owner, false);
            originalDisplay = display;
        }

        @Override
        public GraphicsConfiguration getGraphicsConfiguration() {
            return originalDisplay == null ? super.getGraphicsConfiguration() : originalDisplay;
        }
    }
}

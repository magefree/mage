package mage.client.dialog;

import mage.client.util.gui.GuiDisplayUtil;
import mage.client.util.gui.GuiScalingTestSupport;
import org.junit.*;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class PreferencesPlacementTest extends GuiScalingTestSupport {
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

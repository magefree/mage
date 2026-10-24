package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.ConnectDialog;
import mage.client.dialog.DownloadImagesDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.Assume;
import org.junit.After;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class DialogLayoutTest extends GuiScalingTestSupport {
    private final java.util.List<JFrame> windows = new java.util.ArrayList<>();

    @After
    public void disposeWindows() throws Exception {
        SwingUtilities.invokeAndWait(() -> windows.forEach(JFrame::dispose));
    }

    private JDesktopPane desktop(int width, int height, int fontSize) throws Exception {
        JDesktopPane desktop = new JDesktopPane();
        desktop.setSize(width, height);
        field(MageFrame.class, "desktopPane").set(null, desktop);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(fontSize));
        GUISizeHelper.calculateGUISizes();
        if (!GraphicsEnvironment.isHeadless()) {
            // A hidden peer gives the controls the real display's font measurements.
            JFrame window = new JFrame();
            windows.add(window);
            window.setAutoRequestFocus(false);
            desktop.setPreferredSize(new Dimension(width, height));
            window.add(desktop);
            window.pack();
        }
        return desktop;
    }

    @Test
    public void oversizedConnectionCommandsRemainReachableByScrolling() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = desktop(400, 300, 34);
                ConnectDialog dialog = new ConnectDialog();
                desktop.add(dialog);
                dialog.pack();
                layoutTree(dialog);
                JButton cancel = (JButton) field(ConnectDialog.class, "btnCancel").get(dialog);
                cancel.scrollRectToVisible(new Rectangle(cancel.getSize()));
                assertFullyVisible(cancel);
                assertTrue("Dialog remains within a smaller desktop", dialog.getWidth() <= 400 && dialog.getHeight() <= 300);
                dialog.dispose();
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void connectionUsesSpaceAvailableAfterStartupMaximization() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = desktop(400, 300, 34);
                ConnectDialog dialog = new ConnectDialog();
                desktop.add(dialog);
                dialog.setModal(false);
                dialog.pack();
                desktop.setSize(1920, 950);
                dialog.setVisible(true);
                layoutTree(dialog);
                for (String name : new String[]{"btnConnect", "btnRegister", "btnForgotPassword", "btnCancel"}) {
                    assertFullyVisible((Component) field(ConnectDialog.class, name).get(dialog));
                }
                assertFalse("A dialog that fits no longer needs an outer scrollbar",
                        dialog.getContentPane() instanceof JScrollPane);
                dialog.dispose();
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void downloadWorkerUpdatesRunOnTheEventDispatchThread() throws Exception {
        DownloadImagesDialog[] dialog = new DownloadImagesDialog[1];
        SwingUtilities.invokeAndWait(() -> {
            try {
                desktop(1920, 950, 34);
                dialog[0] = new DownloadImagesDialog();
                JLabel message = (JLabel) field(DownloadImagesDialog.class, "labelGlobal").get(dialog[0]);
                message.addPropertyChangeListener("text", event ->
                        assertTrue("Worker messages must be applied on the EDT", SwingUtilities.isEventDispatchThread()));
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
        dialog[0].setGlobalInfo("Finding missing images...");
        dialog[0].setCurrentInfo("Missing: 12345 card images / 789 token images");
        dialog[0].showDownloadControls(false);
        SwingUtilities.invokeAndWait(() -> {
            layoutTree(dialog[0]);
            assertFullyVisible(dialog[0].getStopButton());
            dialog[0].dispose();
        });
    }

    @Test
    public void downloadLoadingMessageAndCancelFitSelectedFont() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                for (int size : new int[]{14, 17, 23, 34}) {
                    JDesktopPane desktop = desktop(1920, 950, size);
                    DownloadImagesDialog dialog = new DownloadImagesDialog();
                    desktop.add(dialog);
                    dialog.showDownloadControls(false);
                    dialog.setGlobalInfo("Finding missing images...");
                    layoutTree(dialog);
                    assertFullyVisible(dialog.getStopButton());
                    JLabel message = (JLabel) field(DownloadImagesDialog.class, "labelGlobal").get(dialog);
                    assertFullyVisible(message);
                    assertTrue("Complete loading message at font " + size,
                            message.getWidth() >= message.getFontMetrics(message.getFont()).stringWidth(message.getText()));
                    assertTrue(desktop.getBounds().contains(dialog.getBounds()));
                    dialog.dispose();
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void downloadOptionsAndCommandsFitSelectedFont() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                for (int size : new int[]{14, 17, 23, 34}) {
                    JDesktopPane desktop = desktop(1920, 950, size);
                    DownloadImagesDialog dialog = new DownloadImagesDialog();
                    desktop.add(dialog);
                    dialog.setGlobalInfo("");
                    dialog.showDownloadControls(true);
                    layoutTree(dialog);
                    for (Component control : new Component[]{dialog.getSourcesCombo(), dialog.getLaunguagesCombo(),
                            dialog.getDownloadThreadsCombo(), dialog.getSetsCombo(), dialog.getRedownloadCheckbox(),
                            dialog.getStartButton(), dialog.getCancelButton(), dialog.getProgressBar()}) {
                        assertFullyVisible(control);
                        assertTrue("Control height at font " + size + ": " + control,
                                control.getHeight() >= control.getPreferredSize().height);
                    }
                    for (String name : new String[]{"labelInfo", "labelSource", "labelLanguage", "labelDownloadThreads", "labelMode"}) {
                        JLabel label = (JLabel) field(DownloadImagesDialog.class, name).get(dialog);
                        assertFullyVisible(label);
                        assertTrue("Complete label at font " + size, label.getWidth() >= label.getPreferredSize().width);
                    }
                    assertButtonSizes(dialog.getContentPane());
                    assertTrue(desktop.getBounds().contains(dialog.getBounds()));
                    dialog.showDownloadControls(false);
                    layoutTree(dialog);
                    assertFullyVisible(dialog.getStopButton());
                    dialog.dispose();
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    private static void assertFullyVisible(Component component) {
        Rectangle visible = new Rectangle(0, 0, component.getWidth(), component.getHeight());
        assertTrue("Control has a nonempty area", visible.width > 0 && visible.height > 0);
        for (Container parent = component.getParent(); parent != null && !(parent instanceof JDesktopPane);
                component = parent, parent = parent.getParent()) {
            visible.translate(component.getX(), component.getY());
            assertTrue("Control is clipped by " + parent.getClass().getSimpleName() + ": " + visible
                    + " outside " + parent.getSize(),
                    new Rectangle(0, 0, parent.getWidth(), parent.getHeight()).contains(visible));
        }
    }
}

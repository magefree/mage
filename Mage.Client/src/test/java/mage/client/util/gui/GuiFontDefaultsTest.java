package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.ConnectDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static org.junit.Assert.*;

public class GuiFontDefaultsTest extends GuiScalingTestSupport {
    @Test
    public void existingPlayerPanelKeepsItsBaseFontDuringPreferenceRefresh() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "14");
                GUISizeHelper.calculateGUISizes();
                GuiFontDefaults.refresh();
                mage.client.game.PlayerPanelExt panel = new mage.client.game.PlayerPanelExt(1.0f);
                assertEquals(12, panel.getFont().getSize());
                for (int size : new int[]{34, 17, 14}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    GuiFontDefaults.refresh(panel);
                    assertEquals(12, panel.getFont().getSize());
                }
                panel.cleanUp();
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void startupPresetPreservesExistingFontAndPlayerChoices() {
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "34");
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, "23");
        PreferencesDialog.DefaultSizeSettings defaults = PreferencesDialog.getDefaultSizeSettings();
        defaults.applyPresetIfMissing("1366 x 768");
        assertEquals(34, PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, 0));
        assertEquals(23, PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, 0));
        assertEquals(defaults.getPresetValues("1366 x 768").get(1).intValue(),
                PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, 0));
    }

    @Test
    public void playerPanelFontRemainsIndependentOfDialogFont() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                for (int size : new int[]{14, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    GuiFontDefaults.refresh();
                    mage.client.game.PlayerPanelExt panel = new mage.client.game.PlayerPanelExt(1.0f);
                    assertEquals(12, panel.getFont().getSize());
                    JButton hints = (JButton) field(mage.client.game.PlayerPanelExt.class, "toolHintsHelper").get(panel);
                    assertEquals(panel.getFont(), hints.getFont());
                    panel.cleanUp();
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void fontsRefreshExistingAndFutureStandardControlsIndependently() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                JButton existing = new JButton("Choose");
                for (int size : new int[]{14, 17, 23, 34, 14}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, "17");
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, "23");
                    GUISizeHelper.calculateGUISizes();
                    GuiFontDefaults.refresh();
                    GuiFontDefaults.refresh(existing);
                    assertEquals(size, existing.getFont().getSize());
                    assertEquals(size, new JTextField("Text").getFont().getSize());
                    assertEquals(size, new JCheckBox("Check").getFont().getSize());
                    assertEquals(size, new JMenuItem("Menu").getFont().getSize());
                    assertEquals(17, GUISizeHelper.chatFont.getSize());
                    assertEquals(23 / 14.0, GUISizeHelper.playerPanelGuiScale, 0.001);
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void commonDialogCommandsFitLargeFonts() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "34");
                GUISizeHelper.calculateGUISizes();
                for (mage.client.dialog.MageDialog dialog : new mage.client.dialog.MageDialog[]{
                        new mage.client.dialog.AboutDialog(), new mage.client.dialog.AddLandDialog(),
                        new mage.client.dialog.ErrorDialog(), new mage.client.dialog.ResetPasswordDialog(),
                        new mage.client.dialog.NewTableDialog(), new mage.client.dialog.NewTournamentDialog(),
                        new mage.client.dialog.CustomOptionsDialog(mage.client.dialog.CustomOptionsDialog.SaveLoadKeys.TABLE, new JButton("Custom options"))}) {
                    desktop.add(dialog);
                    dialog.pack();
                    layoutTree(dialog);
                    assertButtonSizes(dialog.getContentPane());
                    assertTrue(dialog.getHeight() <= desktop.getHeight());
                    assertTrue(dialog.getWidth() <= desktop.getWidth());
                    dialog.dispose();
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }

    @Test
    public void connectionControlsFitLargeFonts() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                for (int size : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    ConnectDialog connection = new ConnectDialog();
                    desktop.add(connection);
                    connection.pack();
                    layoutTree(connection);
                    JButton cancel = (JButton) field(ConnectDialog.class, "btnCancel").get(connection);
                    assertEquals(size, cancel.getFont().getSize());
                    assertTrue("Cancel width at " + size, cancel.getWidth() >= cancel.getPreferredSize().width);
                    for (String control : new String[]{"lblUserName", "lblFlag", "panelFlag", "jProxySettingsButton", "btnWhatsNew"}) {
                        Component widget = (Component) field(ConnectDialog.class, control).get(connection);
                        assertTrue(control + " height at " + size, widget.getHeight() >= widget.getPreferredSize().height);
                    }
                    JTextField port = (JTextField) field(ConnectDialog.class, "txtPort").get(connection);
                    assertTrue("Five digit port at " + size + ": " + port.getWidth() + " / " + port.getPreferredSize(), port.getWidth() >= port.getFontMetrics(port.getFont()).stringWidth("17171") + port.getInsets().left + port.getInsets().right);
                    assertTrue(connection.getWidth() <= desktop.getWidth());
                    assertTrue(connection.getHeight() <= desktop.getHeight());
                    JLabel status = (JLabel) field(ConnectDialog.class, "lblStatus").get(connection);
                    status.setText("<html>Connection failed:<br>Unable to contact the selected server.<br>Please check the address and try again.</html>");
                    layoutTree(connection);
                    assertTrue(connection.getHeight() <= desktop.getHeight());
                    assertTrue("Error text height at " + size, status.getHeight() >= status.getPreferredSize().height);
                    status.setText("");
                    layoutTree(connection);
                    assertTrue("Cancel after error at " + size, cancel.getHeight() >= cancel.getPreferredSize().height);
                    connection.dispose();

                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }
}

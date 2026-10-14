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
    public void oversizedResizableChoiceFitsDesktopWhenShown() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                for (int size : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    GuiFontDefaults.refresh();
                    mage.client.dialog.PickChoiceDialog dialog = new mage.client.dialog.PickChoiceDialog();
                    dialog.setModal(false);
                    dialog.setSize(1600, 1200);
                    mage.choices.Choice choice = new mage.choices.ChoiceImpl(false);
                    choice.setMessage("Choose commands group to run");
                    choice.setChoices(new LinkedHashSet<>(Arrays.asList("Lands: add to battlefield", "Cards: add to hand")));
                    dialog.showDialog(choice, null, () -> { });
                    layoutTree(dialog);
                    assertTrue("Dialog fits the desktop at " + size, desktop.getBounds().contains(dialog.getBounds()));
                    for (String name : new String[]{"btOK", "btCancel"}) {
                        JButton button = (JButton) field(mage.client.dialog.PickChoiceDialog.class, name).get(dialog);
                        Rectangle bounds = SwingUtilities.convertRectangle(button.getParent(), button.getBounds(), desktop);
                        assertTrue("Choice footer remains visible at " + size, desktop.getBounds().contains(bounds));
                        assertTrue(button.getHeight() >= button.getPreferredSize().height);
                    }
                    dialog.setVisible(false);
                    dialog.setSize(1000, 600);
                    dialog.setVisible(true);
                    assertEquals("Keep a user size that already fits", new Dimension(1000, 600), dialog.getSize());
                    dialog.dispose();
                }
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void layoutRefreshUsesChangedMeasurementsWithoutChangingFonts() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            JPanel root = new JPanel(new BorderLayout());
            JPanel nested = new JPanel();
            final boolean[] largerMetrics = {false};
            JButton button = new JButton("Command") {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(largerMetrics[0] ? 160 : 100, largerMetrics[0] ? 50 : 30);
                }
            };
            GroupLayout layout = new GroupLayout(nested);
            nested.setLayout(layout);
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(button, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(button, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
            root.add(nested);
            root.setSize(400, 200);
            layoutTree(root);
            assertEquals(new Dimension(100, 30), button.getSize());
            Font font = button.getFont();
            largerMetrics[0] = true;
            layoutTree(root);
            assertEquals("The old measurements remain cached", new Dimension(100, 30), button.getSize());
            GuiDisplayUtil.invalidateLayoutTree(root);
            layoutTree(root);
            assertEquals("Nested controls use the new display measurements", new Dimension(160, 50), button.getSize());
            assertSame("Display relayout does not change the selected font", font, button.getFont());
        });
    }

    @Test
    public void fontRefreshUpdatesHiddenPeersAndSkipsDisposedWindows() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            JFrame window = new JFrame();
            final int[] uiUpdates = {0};
            JButton control = new JButton("Hidden window") {
                @Override
                public void updateUI() {
                    super.updateUI();
                    uiUpdates[0]++;
                }
            };
            try {
                window.add(control);
                window.pack();
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "34");
                GUISizeHelper.calculateGUISizes();
                GuiFontDefaults.refresh();
                assertEquals("A hidden live peer receives the new font", 34, control.getFont().getSize());
                assertFalse(window.isVisible());
                window.dispose();
                int before = uiUpdates[0];
                PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "17");
                GUISizeHelper.calculateGUISizes();
                GuiFontDefaults.refresh();
                assertEquals("Disposed windows do not receive UI updates", before, uiUpdates[0]);
            } finally {
                window.dispose();
            }
        });
    }

    @Test
    public void nimbusTabStatesKeepTheDialogFont() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                for (int size : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    GuiFontDefaults.refresh();
                    JTabbedPane tabs = new JTabbedPane();
                    assertEquals("Refresh preserves the selected font", size, GUISizeHelper.dialogFont.getSize());
                    javax.swing.plaf.synth.Region region = javax.swing.plaf.synth.Region.TABBED_PANE_TAB;
                    javax.swing.plaf.synth.SynthStyle style = javax.swing.plaf.synth.SynthLookAndFeel.getStyle(tabs, region);
                    for (int state : new int[]{javax.swing.plaf.synth.SynthConstants.ENABLED,
                            javax.swing.plaf.synth.SynthConstants.ENABLED | javax.swing.plaf.synth.SynthConstants.SELECTED,
                            javax.swing.plaf.synth.SynthConstants.ENABLED | javax.swing.plaf.synth.SynthConstants.MOUSE_OVER}) {
                        assertEquals("Tab font in state " + state, size,
                                style.getFont(new javax.swing.plaf.synth.SynthContext(tabs, region, style, state)).getSize());
                    }
                }
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

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
                    assertEquals("Refresh preserves the selected font", size, GUISizeHelper.dialogFont.getSize());
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

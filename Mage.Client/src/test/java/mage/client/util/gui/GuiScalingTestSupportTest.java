package mage.client.util.gui;

import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class GuiScalingTestSupportTest {
    @Test
    public void fixtureRestoresLookAndFeelFontsAndSplitPaneBindings() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            LookAndFeel beforeLookAndFeel = UIManager.getLookAndFeel();
            Font beforeFont = UIManager.getFont("Button.font");
            InputMap beforeBindings = (InputMap) UIManager.get("SplitPane.ancestorInputMap");
            KeyStroke f6 = KeyStroke.getKeyStroke("F6");
            Object beforeF6 = beforeBindings.get(f6);
            GuiScalingTestSupport fixture = new GuiScalingTestSupport() { };
            try {
                fixture.isolatePreferences();
                try {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, "34");
                    GUISizeHelper.calculateGUISizes();
                    assertEquals(beforeFont, UIManager.getFont("Button.font"));
                    UIManager.put("Button.font", new javax.swing.plaf.FontUIResource(GUISizeHelper.dialogFont));
                } finally {
                    fixture.restorePreferences();
                }
                assertSame(beforeLookAndFeel, UIManager.getLookAndFeel());
                assertEquals(beforeFont, UIManager.getFont("Button.font"));
                assertEquals(beforeF6, ((InputMap) UIManager.get("SplitPane.ancestorInputMap")).get(f6));
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }
}

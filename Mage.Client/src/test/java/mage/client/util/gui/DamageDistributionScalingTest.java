package mage.client.util.gui;

import mage.client.MageFrame;
import mage.client.dialog.PickMultiNumberDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import mage.util.MultiAmountMessage;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.Assert.*;

public class DamageDistributionScalingTest extends GuiScalingTestSupport {
    @Test
    public void damageTargetsAndTotalsRemainCorrectAtLargeFonts() throws Exception {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                for (int size : new int[]{14, 17, 23, 34}) {
                    PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(size));
                    GUISizeHelper.calculateGUISizes();
                    PickMultiNumberDialog damage = new PickMultiNumberDialog();
                    desktop.add(damage);
                    Map<String, java.io.Serializable> options = new HashMap<>();
                    options.put("header", "Assign 10 damage");
                    options.put("title", "Damage distribution");
                    damage.showDialog(Arrays.asList(new MultiAmountMessage("Kambal, Consul of Allocation", 1, 9, 8),
                            new MultiAmountMessage("High Priest of Penance", 1, 9, 2)), 10, 10, options, null);
                    layoutTree(damage);
                    JScrollPane scroll = (JScrollPane) field(PickMultiNumberDialog.class, "jScrollPane1").get(damage);
                    JPanel rows = (JPanel) field(PickMultiNumberDialog.class, "jPanel1").get(damage);
                    assertTrue("Both rows visible at " + size + ": " + scroll.getViewport().getExtentSize() + " / " + rows.getPreferredSize(), scroll.getViewport().getExtentSize().height >= rows.getPreferredSize().height);
                    assertTrue("No horizontal scrolling at " + size, scroll.getViewport().getExtentSize().width >= rows.getPreferredSize().width);
                    JButton choose = (JButton) field(PickMultiNumberDialog.class, "btnOk").get(damage);
                    assertTrue(choose.isEnabled());
                    assertEquals("8 2", damage.getMultiAmount());
                    List<JSpinner> spinners = (List<JSpinner>) field(PickMultiNumberDialog.class, "spinnerList").get(damage);
                    spinners.get(0).setValue(7);
                    assertFalse(choose.isEnabled());
                    spinners.get(1).setValue(3);
                    assertTrue(choose.isEnabled());
                    assertEquals("7 3", damage.getMultiAmount());
                    damage.dispose();
                }
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        });
    }
}

package mage.client.dialog;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import mage.client.MageFrame;

import static mage.client.util.PhaseManager.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by IntelliJ IDEA.
 * User: Администратор
 * Date: 12.12.10
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class PhasesDialog {
    private JPanel panel1;
    private JCheckBox checkBoxUpkeepYou;
    private JCheckBox checkBoxUpkeepOthers;
    private JCheckBox checkBoxDrawYou;
    private JCheckBox checkBoxMainYou;
    private JCheckBox checkBoxBeforeCYou;
    private JCheckBox checkBoxEndCYou;
    private JCheckBox checkBoxMain2You;
    private JCheckBox checkBoxEndTurnYou;
    private JCheckBox checkBoxDrawOthers;
    private JCheckBox checkBoxMainOthers;
    private JCheckBox checkBoxBeforeCOthers;
    private JCheckBox checkBoxEndCOthers;
    private JCheckBox checkBoxMain2Others;
    private JCheckBox checkBoxEndTurnOthers;
    private JButton OKButton;
    private JButton cancelButton;

    private static final JFrame fInstance = new JFrame("PhasesDialog");
    private static final PhasesDialog dialog = new PhasesDialog();

    static {
        fInstance.setContentPane(dialog.panel1);
        fInstance.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        fInstance.setLocation(300, 200);
        fInstance.setResizable(false);
        fInstance.setTitle("Configure phase stops");
        fInstance.pack();
    }

    public static void main(String[] args) {
        if (!fInstance.isVisible()) {
            Preferences prefs = MageFrame.getPreferences();
            load(prefs, dialog.checkBoxUpkeepYou, UPKEEP_YOU);
            load(prefs, dialog.checkBoxDrawYou, DRAW_YOU);
            load(prefs, dialog.checkBoxMainYou, MAIN_YOU);
            load(prefs, dialog.checkBoxBeforeCYou, BEFORE_COMBAT_YOU);
            load(prefs, dialog.checkBoxEndCYou, END_OF_COMBAT_YOU);
            load(prefs, dialog.checkBoxMain2You, MAIN_2_YOU);
            load(prefs, dialog.checkBoxEndTurnYou, END_OF_TURN_YOU);

            load(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
            load(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
            load(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
            load(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
            load(prefs, dialog.checkBoxEndCOthers, END_OF_COMBAT_OTHERS);
            load(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS);
            load(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);
            fInstance.setVisible(true);
        } else {
            fInstance.requestFocus();
        }
    }

    private static void load(Preferences prefs, JCheckBox checkBox, String propName) {
        String prop = prefs.get(propName, PHASE_ON);
        checkBox.setSelected(prop.equals(PHASE_ON));
    }

    private static void save(Preferences prefs, JCheckBox checkBox, String propName) {
        prefs.put(propName, checkBox.isSelected() ? PHASE_ON : PHASE_OFF);
    }

    public PhasesDialog() {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Preferences prefs = MageFrame.getPreferences();
                save(prefs, dialog.checkBoxUpkeepYou, "upkeepYou");
                save(prefs, dialog.checkBoxDrawYou, "drawYou");
                save(prefs, dialog.checkBoxMainYou, "mainYou");
                save(prefs, dialog.checkBoxBeforeCYou, "beforeCombatYou");
                save(prefs, dialog.checkBoxEndCYou, "endOfCombatYou");
                save(prefs, dialog.checkBoxMain2You, "main2You");
                save(prefs, dialog.checkBoxEndTurnYou, "endOfTurnYou");

                save(prefs, dialog.checkBoxUpkeepOthers, UPKEEP_OTHERS);
                save(prefs, dialog.checkBoxDrawOthers, DRAW_OTHERS);
                save(prefs, dialog.checkBoxMainOthers, MAIN_OTHERS);
                save(prefs, dialog.checkBoxBeforeCOthers, BEFORE_COMBAT_OTHERS);
                save(prefs, dialog.checkBoxEndCOthers, END_OF_COMBAT_OTHERS);
                save(prefs, dialog.checkBoxMain2Others, MAIN_2_OTHERS);
                save(prefs, dialog.checkBoxEndTurnOthers, END_OF_TURN_OTHERS);
                try {
                    prefs.flush();
                } catch (BackingStoreException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: couldn't save phase stops. Please try again.");
                }
                fInstance.setVisible(false);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fInstance.setVisible(false);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new FormLayout("fill:324px:noGrow", "center:d:noGrow,top:4dlu:noGrow,center:258px:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        panel1.setPreferredSize(new Dimension(324, 324));
        final JLabel label1 = new JLabel();
        label1.setMaximumSize(new Dimension(88, 20));
        label1.setMinimumSize(new Dimension(88, 20));
        label1.setPreferredSize(new Dimension(88, 20));
        label1.setText("Stop on (yes/no)");
        CellConstraints cc = new CellConstraints();
        panel1.add(label1, new CellConstraints(1, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.DEFAULT, new Insets(5, 10, 0, 0)));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setMinimumSize(new Dimension(0, 0));
        panel2.setPreferredSize(new Dimension(300, 250));
        panel1.add(panel2, new CellConstraints(1, 3, 1, 1, CellConstraints.LEFT, CellConstraints.TOP, new Insets(0, 18, 0, 0)));
        final JLabel label2 = new JLabel();
        label2.setText("Upkeep:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Draw:");
        panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Main:");
        panel2.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Before combat:");
        panel2.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("End of combat:");
        panel2.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Main 2:");
        panel2.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("End of turn:");
        panel2.add(label8, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 16), null, 0, false));
        checkBoxUpkeepYou = new JCheckBox();
        checkBoxUpkeepYou.setSelected(true);
        checkBoxUpkeepYou.setText("");
        panel2.add(checkBoxUpkeepYou, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxDrawYou = new JCheckBox();
        checkBoxDrawYou.setSelected(true);
        checkBoxDrawYou.setText("");
        panel2.add(checkBoxDrawYou, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxMainYou = new JCheckBox();
        checkBoxMainYou.setSelected(true);
        checkBoxMainYou.setText("");
        panel2.add(checkBoxMainYou, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxBeforeCYou = new JCheckBox();
        checkBoxBeforeCYou.setSelected(true);
        checkBoxBeforeCYou.setText("");
        panel2.add(checkBoxBeforeCYou, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxEndCYou = new JCheckBox();
        checkBoxEndCYou.setSelected(true);
        checkBoxEndCYou.setText("");
        panel2.add(checkBoxEndCYou, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxMain2You = new JCheckBox();
        checkBoxMain2You.setSelected(true);
        checkBoxMain2You.setText("");
        panel2.add(checkBoxMain2You, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxEndTurnYou = new JCheckBox();
        checkBoxEndTurnYou.setSelected(true);
        checkBoxEndTurnYou.setText("");
        panel2.add(checkBoxEndTurnYou, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxUpkeepOthers = new JCheckBox();
        checkBoxUpkeepOthers.setSelected(true);
        checkBoxUpkeepOthers.setText("");
        panel2.add(checkBoxUpkeepOthers, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxDrawOthers = new JCheckBox();
        checkBoxDrawOthers.setSelected(true);
        checkBoxDrawOthers.setText("");
        panel2.add(checkBoxDrawOthers, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxMainOthers = new JCheckBox();
        checkBoxMainOthers.setSelected(true);
        checkBoxMainOthers.setText("");
        panel2.add(checkBoxMainOthers, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxBeforeCOthers = new JCheckBox();
        checkBoxBeforeCOthers.setSelected(true);
        checkBoxBeforeCOthers.setText("");
        panel2.add(checkBoxBeforeCOthers, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxEndCOthers = new JCheckBox();
        checkBoxEndCOthers.setSelected(true);
        checkBoxEndCOthers.setText("");
        panel2.add(checkBoxEndCOthers, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxMain2Others = new JCheckBox();
        checkBoxMain2Others.setSelected(true);
        checkBoxMain2Others.setText("");
        panel2.add(checkBoxMain2Others, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        checkBoxEndTurnOthers = new JCheckBox();
        checkBoxEndTurnOthers.setSelected(true);
        checkBoxEndTurnOthers.setText("");
        panel2.add(checkBoxEndTurnOthers, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(10, 21), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("");
        panel2.add(label9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(107, 0), null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setHorizontalTextPosition(0);
        label10.setText("Your turn");
        panel2.add(label10, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(61, 16), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setHorizontalTextPosition(0);
        label11.setText("Others turn");
        panel2.add(label11, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(61, 16), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panel1.add(panel3, cc.xy(1, 5));
        OKButton = new JButton();
        OKButton.setInheritsPopupMenu(true);
        OKButton.setLabel("Save");
        OKButton.setText("Save");
        panel3.add(OKButton);
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setMnemonic('C');
        cancelButton.setDisplayedMnemonicIndex(0);
        panel3.add(cancelButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}

package mage.client.util.gui;

import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.client.MageFrame;
import mage.client.dialog.CheckBoxList;
import mage.client.dialog.PickCheckBoxDialog;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.GUISizeHelper;
import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import static org.junit.Assert.*;

public class DeckFilterInteractionTest extends GuiScalingTestSupport {
    private PickCheckBoxDialog createDialog(int font) throws Exception {
        JDesktopPane desktop = new JDesktopPane();
        desktop.setSize(1366, 700);
        field(MageFrame.class, "desktopPane").set(null, desktop);
        PreferencesDialog.saveValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, String.valueOf(font));
        GUISizeHelper.calculateGUISizes();
        CheckBoxList list = new CheckBoxList();
        list.setListData(new String[]{"Alpha", "Beta", "Gamma"});
        list.setChecked(0, true);
        list.setChecked(2, true);
        Choice choice = new ChoiceImpl(false);
        Map<String, String> values = new LinkedHashMap<>();
        Map<String, Integer> sort = new LinkedHashMap<>();
        for (String value : new String[]{"Alpha", "Beta", "Gamma"}) {
            sort.put(value, values.size());
            values.put(value, value);
        }
        choice.setKeyChoices(values);
        choice.setSortData(sort);
        choice.setMessage("Choose sets");
        PickCheckBoxDialog dialog = new PickCheckBoxDialog(list);
        desktop.add(dialog);
        dialog.setWindowSize(300, 500);
        dialog.showDialog(choice, "Alpha", () -> { });
        layoutTree(dialog);
        return dialog;
    }

    @Test
    public void deckFilterCommandsFitConfiguredFont() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                for (int font : new int[]{14, 17, 23, 34}) {
                    PickCheckBoxDialog dialog = createDialog(font);
                    assertButtonSizes(dialog.getContentPane());
                    dialog.dispose();
                }
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void searchKeyboardNavigationSelectsVisibleList() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                PickCheckBoxDialog dialog = createDialog(34);
                CheckBoxList list = (CheckBoxList) field(PickCheckBoxDialog.class, "tList").get(dialog);
                JTextField search = (JTextField) field(PickCheckBoxDialog.class, "editSearch").get(dialog);
                list.setSelectedIndex(0);
                KeyEvent down = new KeyEvent(search, KeyEvent.KEY_PRESSED, 0, 0, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED);
                for (KeyListener listener : search.getKeyListeners()) listener.keyPressed(down);
                assertEquals(1, list.getSelectedIndex());
                dialog.dispose();
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void plainChoiceCanBeChosenWithoutCheckboxModel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                JDesktopPane desktop = new JDesktopPane();
                desktop.setSize(1366, 700);
                field(MageFrame.class, "desktopPane").set(null, desktop);
                Choice choice = new ChoiceImpl(false);
                Map<String, String> choices = new LinkedHashMap<>();
                choices.put("A", "Alpha");
                choices.put("B", "Beta");
                choice.setKeyChoices(choices);
                PickCheckBoxDialog dialog = new PickCheckBoxDialog();
                desktop.add(dialog);
                dialog.showDialog(choice, () -> { });
                JList list = (JList) field(PickCheckBoxDialog.class, "listChoices").get(dialog);
                list.setSelectedIndex(1);
                ((JButton) field(PickCheckBoxDialog.class, "btOK").get(dialog)).doClick(0);
                assertTrue(choice.isChosen());
                assertEquals("B", choice.getChoiceKey());
                dialog.dispose();
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }

    @Test
    public void cancellingFilteredSearchRestoresEverySetAndCheck() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                PickCheckBoxDialog dialog = createDialog(34);
                CheckBoxList list = (CheckBoxList) field(PickCheckBoxDialog.class, "tList").get(dialog);
                JTextField search = (JTextField) field(PickCheckBoxDialog.class, "editSearch").get(dialog);
                search.setText("Beta");
                assertEquals(1, list.getModel().getSize());
                list.setChecked(0, true);
                Action cancel = dialog.getRootPane().getActionMap().get("cancel");
                cancel.actionPerformed(new ActionEvent(search, ActionEvent.ACTION_PERFORMED, "cancel"));
                assertEquals(3, list.getModel().getSize());
                assertArrayEquals(new int[]{0, 2}, list.getCheckedIndices());
                dialog.dispose();
            } catch (Exception error) { throw new RuntimeException(error); }
        });
    }
}

package mage.client.util.gui;

import mage.choices.ChoiceImpl;
import mage.client.dialog.CheckBoxList;
import mage.client.dialog.PickCheckBoxDialog;
import mage.client.dialog.PickChoiceDialog;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JayDi85
 */
public class FastSearchUtil {

    public static final String DEFAULT_EXPANSION_SEARCH_MESSAGE = "Select set or expansion";
    public static final String DEFAULT_EXPANSION_TOOLTIP_MESSAGE = "Fast search set or expansion";

    public static void showFastSearchForStringComboBox(JComboBox combo, String chooseMessage) {
        showFastSearchForStringComboBox(combo, chooseMessage, 300, 500);
    }

    /**
     * Show fast choice modal dialog with incremental searching for any string combobox components
     *
     * @param combo         combobox control with default data model
     * @param chooseMessage caption message for dialog
     */
    public static void showFastSearchForStringComboBox(JComboBox combo, String chooseMessage, int windowWidth, int windowHeight) {
        // fast search/choice dialog for string combobox

        mage.choices.Choice choice = new ChoiceImpl(false);

        // collect data from expansion combobox (String)
        DefaultComboBoxModel comboModel = (DefaultComboBoxModel) combo.getModel();
        Map<String, String> choiceItems = new HashMap<>(comboModel.getSize());
        Map<String, Integer> choiceSorting = new HashMap<>(comboModel.getSize());
        String item;

        for (int i = 0; i < comboModel.getSize(); i++) {
            item = comboModel.getElementAt(i).toString();
            choiceItems.put(item, item);
            choiceSorting.put(item, i); // need so sorting
        }

        choice.setKeyChoices(choiceItems);
        choice.setSortData(choiceSorting);
        choice.setMessage(chooseMessage);

        // current selection value restore
        String needSelectValue;
        needSelectValue = comboModel.getSelectedItem().toString();

        // ask for new value
        PickChoiceDialog dlg = new PickChoiceDialog();
        dlg.setWindowSize(windowWidth, windowHeight);
        dlg.showDialog(choice, needSelectValue);
        if (choice.isChosen()) {
            item = choice.getChoiceKey();

            // compatible select for object's models (use setSelectedIndex instead setSelectedObject)
            for (int i = 0; i < comboModel.getSize(); i++) {
                if (comboModel.getElementAt(i).toString().equals(item)) {
                    combo.setSelectedIndex(i);
                }
            }
        }
    }

    /**
     * Show fast choice modal dialog with incremental searching for any string CheckBoxList components
     *
     * @param combo         CheckBoxList control with default data model
     * @param chooseMessage caption message for dialog
     */
    public static void showFastSearchForStringComboBox(CheckBoxList combo, String chooseMessage) {
        // fast search/choice dialog for string combobox

        mage.choices.Choice choice = new ChoiceImpl(false);

        // collect data from expansion combobox (String)
        DefaultListModel comboModel = (DefaultListModel) combo.getModel();
        Map<String, String> choiceItems = new HashMap<>(comboModel.getSize());
        Map<String, Integer> choiceSorting = new HashMap<>(comboModel.getSize());
        String item;

        for (int i = 0; i < comboModel.size(); i++) {
            item = comboModel.getElementAt(i).toString();
            choiceItems.put(item, item);
            choiceSorting.put(item, i); // need so sorting
        }

        choice.setKeyChoices(choiceItems);
        choice.setSortData(choiceSorting);
        choice.setMessage(chooseMessage);

        // current selection value restore
        String needSelectValue = null;
        if (comboModel.size() > 0) {
            needSelectValue = comboModel.firstElement().toString();
        }

        // ask for new value
        PickCheckBoxDialog dlg = new PickCheckBoxDialog(combo);

        dlg.setWindowSize(300, 500);
        dlg.showDialog(choice, needSelectValue);
        if (choice.isChosen()) {
            item = choice.getChoiceKey();

            // compatible select for object's models (use setSelectedIndex instead setSelectedObject)
            for (int i = 0; i < comboModel.getSize(); i++) {
                if (comboModel.getElementAt(i).toString().equals(item)) {
                    combo.setSelectedIndex(i);
                }
            }
        }
    }
}

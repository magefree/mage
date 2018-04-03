package mage.client.util.gui;

import java.util.ArrayList;
import mage.choices.ChoiceImpl;
import mage.client.dialog.PickChoiceDialog_1;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import mage.client.dialog.CheckBoxList;
//import org.apache.log4j.helpers.LogLog;

/**
 *
 * @author JayDi85
 */
public class FastSearchUtil_1 {

    public static String DEFAULT_EXPANSION_SEARCH_MESSAGE = "Select set or expansion";
    public static String DEFAULT_EXPANSION_TOOLTIP_MESSAGE = "Fast search set or expansion";

    /**
     * Show fast choice modal dialog with incremental searching for any string combobox components
     * @param combo combobox control with default data model
     * @param chooseMessage caption message for dialog
     * @return The list of all item
     */
    public static CheckBoxList showFastSearchForStringComboBox(JComboBox combo, String chooseMessage){
        // fast search/choice dialog for string combobox

        CheckBoxList langList= new CheckBoxList();
        java.util.List<String> myList = new ArrayList<>();
        
        
    
        
        mage.choices.Choice choice = new ChoiceImpl(false);

        // collect data from expansion combobox (String)
        DefaultComboBoxModel comboModel = (DefaultComboBoxModel)combo.getModel();
        Map<String, String> choiceItems = new HashMap<>(comboModel.getSize());
        Map<String, Integer> choiceSorting = new HashMap<>(comboModel.getSize());
        String item;
        
        for(int i = 0; i < comboModel.getSize(); i++){
            item = comboModel.getElementAt(i).toString();
            myList.add(item);
            choiceItems.put(item, item);
            choiceSorting.put(item, i); // need so sorting
        }
        
        langList.setListData(myList.toArray());
        langList.setChecked(0, true);
        langList.setChecked(3, true);
        choice.setKeyChoices(choiceItems);
        choice.setSortData(choiceSorting);
        choice.setMessage(chooseMessage);

        // current selection value restore
        String needSelectValue;
        needSelectValue = comboModel.getSelectedItem().toString();

        // ask for new value
        PickChoiceDialog_1<CheckBoxList> dlg = new PickChoiceDialog_1(langList);
        dlg.setWindowSize(300, 500);
        //dlg.set(langList.);
        dlg.showDialog(choice, needSelectValue);
        /*int[] choiseValue=langList.getCheckedIndices();
        LogLog.warn("selected:"); 
        for(int itemIndex: choiseValue){
           
            LogLog.warn(String.format("%d",itemIndex));
        }*/
        if(choice.isChosen()){
            item = choice.getChoiceKey();

            // compatible select for object's models (use setSelectedIndex instead setSelectedObject)
            for(int i = 0; i < comboModel.getSize(); i++){
                if(comboModel.getElementAt(i).toString().equals(item)){
                    combo.setSelectedIndex(i);
                }
            }
        }
        return langList;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util.gui;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import mage.client.dialog.PreferencesDialog;
import org.mage.card.arcane.Util;

/**
 *
 * @author LevelX2
 */
public class TableUtil {

    /**
     * 
     * @param table
     * @param defaultColumnsWidth 
     * @param widthPrefKey 
     * @param orderPrefKey 
     */
    static public void setColumnWidthAndOrder(JTable table, int[] defaultColumnsWidth, String widthPrefKey, String orderPrefKey) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // set the column width from saved value or defaults        
        int[] widths = getIntArrayFromString(PreferencesDialog.getCachedValue(widthPrefKey, null));
        int i = 0;
        for (int width : defaultColumnsWidth) {
            if (widths != null && widths.length > i) {
                width = widths[i];
            }
            TableColumn column = table.getColumnModel().getColumn(i++);
            column.setWidth(width);
            column.setPreferredWidth(width);
        }

        // set the column order
        int[] order = getIntArrayFromString(PreferencesDialog.getCachedValue(orderPrefKey, null));
        if (order != null && order.length == table.getColumnCount()) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.moveColumn(table.convertColumnIndexToView(order[j]), j);
            }
        }

    }
    
    static public void saveColumnWidthAndOrderToPrefs(JTable table, String widthPrefKey, String orderPrefKey) {
        // Column width
        StringBuilder columnWidthSettings = new StringBuilder();
        StringBuilder columnOrderSettings = new StringBuilder();
        boolean firstValue = true;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(table.convertColumnIndexToView(i));
            if (!firstValue) {
                columnWidthSettings.append(",");
                columnOrderSettings.append(",");
            } else {
                firstValue = false;
            }
            columnWidthSettings.append(column.getWidth());
            columnOrderSettings.append(table.convertColumnIndexToModel(i));
        }
        PreferencesDialog.saveValue(widthPrefKey, columnWidthSettings.toString());
        PreferencesDialog.saveValue(orderPrefKey, columnOrderSettings.toString());

    }
    
    
    public static int[] getIntArrayFromString(String stringData) {
        int[] intArray = null;
        if (stringData != null && !stringData.isEmpty()) {
            String[] items = stringData.split(",");
            int lengthW = items.length;
            intArray = new int[lengthW];
            for (int i = 0; i < lengthW; i++) {
                try {
                    intArray[i] = Integer.parseInt(items[i]);
                } catch (NumberFormatException nfe) {}
            }
        } 
        return intArray;
    }    
}

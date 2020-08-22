/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util.gui;

import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.TableColumn;
import org.apache.log4j.Logger;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public final class TableUtil {

    /**
     *
     * @param table
     * @param defaultColumnsWidth
     * @param widthPrefKey
     * @param orderPrefKey
     */

    private static final Logger LOGGER = Logger.getLogger(TableUtil.class);

    public static void saveActiveFiltersToPrefs(String filterPrefKey, JToggleButton[] buttons) {
      StringBuilder currentFilters = new StringBuilder();
      for (JToggleButton component : buttons) {
          currentFilters.append(component.isSelected() ? "x" : "-");
      }

      PreferencesDialog.saveValue(filterPrefKey, currentFilters.toString());
    }

    public static void setActiveFilters(String filterPrefKey, JToggleButton[] buttons) {
        String formatSettings = PreferencesDialog.getCachedValue(filterPrefKey, "");
        int i = 0;
        for (JToggleButton component : buttons) {
            if (formatSettings.length() > i) {
                component.setSelected(formatSettings.substring(i, i + 1).equals("x"));
            } else {
                component.setSelected(true);
            }
            i++;
        }
    }

    public static void setColumnWidthAndOrder(JTable table, int[] defaultColumnsWidth, String widthPrefKey, String orderPrefKey) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // set the column width from saved value or defaults
        int[] widths = getIntArrayFromString(PreferencesDialog.getCachedValue(widthPrefKey, null));
        int i = 0;
        for (int width : defaultColumnsWidth) {
            if (widths != null && widths.length > i) {
                width = widths[i];
            }
            if (table.getColumnModel().getColumnCount() >= i) {
                TableColumn column = table.getColumnModel().getColumn(i++);
                column.setWidth(width);
                column.setPreferredWidth(width);
            } else {
                break;
            }
        }

        // set the column order
        int[] order = getIntArrayFromString(PreferencesDialog.getCachedValue(orderPrefKey, null));
        if (order != null && order.length == table.getColumnCount()) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                table.moveColumn(table.convertColumnIndexToView(order[j]), j);
            }
        }

    }

    public static void saveColumnWidthAndOrderToPrefs(JTable table, String widthPrefKey, String orderPrefKey) {
        StringBuilder columnWidthSettings = new StringBuilder();
        StringBuilder columnOrderSettings = new StringBuilder();
        boolean firstValue = true;

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(table.convertColumnIndexToView(i));
            if (!firstValue) {
                columnWidthSettings.append(',');
                columnOrderSettings.append(',');
            } else {
                firstValue = false;
            }
            columnWidthSettings.append(column.getWidth());
            columnOrderSettings.append(table.convertColumnIndexToModel(i));
        }

        PreferencesDialog.saveValue(widthPrefKey, columnWidthSettings.toString());
        PreferencesDialog.saveValue(orderPrefKey, columnOrderSettings.toString());
    }

    private static int[] getIntArrayFromString(String stringData) {
        int[] intArray = null;
        if (stringData != null && !stringData.isEmpty()) {
            String[] items = stringData.split(",");
            int lengthW = items.length;
            intArray = new int[lengthW];
            for (int i = 0; i < lengthW; i++) {
                try {
                    intArray[i] = Integer.parseInt(items[i]);
                } catch (NumberFormatException nfe) {
                }
            }
        }
        return intArray;
    }
}

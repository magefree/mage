/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import java.awt.Font;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public class FontSizeHelper {

    public static String basicSymbolSize = "small";

    public static int symbolCardSize = 15;
    public static int symbolTooltipSize = 15;
    public static int symbolPaySize = 15;
    public static int symbolEditorSize = 15;

    public static int tableHeaderHeight = 24;
    public static int tableRowHeight = 20;

    public static int dividerBarSize;
    public static int scrollBarSize;

    public static Font tooltipFont = new java.awt.Font("Arial", 0, 12);

    public static Font menuFont = new java.awt.Font("Arial", 0, 12);

    public static Font gameDialogAreaFontBig = new java.awt.Font("Arial", 0, 12);
    public static Font gameDialogAreaFontSmall = new java.awt.Font("Arial", 0, 12);

    public static Font getChatFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static Font getTableFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static Font getToolbarFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static int getTableRowHeight() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        return fontSize + 6;
    }

    public static Font getTabFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static void changeGUISize() {
        calculateGUISizes();
        MageFrame.getInstance().changeGUISize();
    }

    public static void calculateGUISizes() {
        // Set basic symbol size
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FONT_SIZE, 14);
        if (fontSize < 25) {
            basicSymbolSize = "small";
        } else if (fontSize < 45) {
            basicSymbolSize = "medium";
        } else {
            basicSymbolSize = "large";
        }
        if (fontSize > 15) {
            symbolTooltipSize = fontSize - 5;
            symbolEditorSize = fontSize - 5;
            symbolPaySize = fontSize - 5;
            symbolCardSize = 15;
            dividerBarSize = 10 + (fontSize / 4);
            scrollBarSize = 14 + (fontSize / 4);
        } else {
            symbolTooltipSize = fontSize;
            symbolEditorSize = fontSize;
            symbolPaySize = fontSize;
            symbolCardSize = 15;
            dividerBarSize = 10;
            scrollBarSize = 14;
        }
        tooltipFont = new java.awt.Font("Arial", 0, fontSize - 2);
        // used for popup menus
        menuFont = new java.awt.Font("Arial", 0, fontSize);

        tableRowHeight = fontSize + 4;
        tableHeaderHeight = fontSize + 10;

    }
}

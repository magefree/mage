/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import java.awt.Dimension;
import java.awt.Font;
import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;

/**
 *
 * @author LevelX2
 */
public class GUISizeHelper {

    // relate the native image card size to a value of the size scale
    final static int CARD_IMAGE_WIDTH = 312;
    final static int CARD_IMAGE_HEIGHT = 445;
    final static int CARD_IMAG_VALUE = 42;

    public static String basicSymbolSize = "small";

    public static int symbolCardSize = 15;
    public static int symbolTooltipSize = 15;
    public static int symbolPaySize = 15;
    public static int symbolEditorSize = 15;

    public static int tableHeaderHeight = 24;
    public static int tableRowHeight = 20;

    public static int dividerBarSize;
    public static int scrollBarSize;

    public static int flagHeight;

    public static Font chatFont = new java.awt.Font("Arial", 0, 12);
    public static Font tableFont = new java.awt.Font("Arial", 0, 12);
    public static Font tooltipFont = new java.awt.Font("Arial", 0, 12);
    public static Font menuFont = new java.awt.Font("Arial", 0, 12);

    public static Font gameDialogAreaFontBig = new java.awt.Font("Arial", 0, 12);
    public static Font gameDialogAreaFontSmall = new java.awt.Font("Arial", 0, 12);

    public static Dimension handCardDimension;

    public static Font getToolbarFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static int getTableRowHeight() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        return fontSize + 6;
    }

    public static Font getTabFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static void changeGUISize() {
        calculateGUISizes();
        MageFrame.getInstance().changeGUISize();
    }

    public static void calculateGUISizes() {
        int tableFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        tableFont = new java.awt.Font("Arial", 0, tableFontSize);
        tableRowHeight = tableFontSize + 4;
        tableHeaderHeight = tableFontSize + 10;

        tooltipFont = new java.awt.Font("Arial", 0, tableFontSize - 2);
        // used for popup menus
        menuFont = new java.awt.Font("Arial", 0, tableFontSize);

        int chatFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, 14);
        chatFont = new java.awt.Font("Arial", 0, chatFontSize);

        // Set basic symbol size
        if (tableFontSize > 24) {
            flagHeight = tableFontSize - 4;
        } else {
            flagHeight = 11;
        }
        if (tableFontSize < 25) {
            basicSymbolSize = "small";
        } else if (tableFontSize < 45) {
            basicSymbolSize = "medium";
        } else {
            basicSymbolSize = "large";
        }
        if (tableFontSize > 15) {
            symbolTooltipSize = tableFontSize - 5;
            symbolEditorSize = tableFontSize - 5;
            symbolPaySize = tableFontSize - 5;
            symbolCardSize = 15;
            dividerBarSize = 10 + (tableFontSize / 4);
            scrollBarSize = 14 + (tableFontSize / 4);
        } else {
            symbolTooltipSize = tableFontSize;
            symbolEditorSize = tableFontSize;
            symbolPaySize = tableFontSize;
            symbolCardSize = 15;
            dividerBarSize = 10;
            scrollBarSize = 14;
        }

        int handCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_HAND_SIZE, 14);
        int width = CARD_IMAGE_WIDTH * handCardSize / 42;
        int height = CARD_IMAGE_HEIGHT * handCardSize / 42;

        handCardDimension = new Dimension(width, height);
    }
}

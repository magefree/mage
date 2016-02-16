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
    public static int symbolTableSize = 15;
    public static int symbolChatSize = 15;
    public static int symbolDialogSize = 15;
    public static int symbolTooltipSize = 15;
    public static int symbolPaySize = 15;
    public static int symbolEditorSize = 15;

    public static int tableHeaderHeight = 24;
    public static int tableRowHeight = 20;

    public static int dividerBarSize;
    public static int scrollBarSize;

    public static int flagHeight;

    public static int cardTooltipFontSize = 15;
    public static Font chatFont = new java.awt.Font("Arial", 0, 12);
    public static Font tableFont = new java.awt.Font("Arial", 0, 12);
    public static Font balloonTooltipFont = new java.awt.Font("Arial", 0, 12);
    public static Font menuFont = new java.awt.Font("Arial", 0, 12);

    public static Font gameRequestsFont = new java.awt.Font("Arial", 0, 12);

    public static Font gameDialogAreaFontBig = new java.awt.Font("Arial", 0, 12);
    public static Font gameDialogAreaFontSmall = new java.awt.Font("Arial", 0, 12);

    public static Dimension handCardDimension;
    public static Dimension otherZonesCardDimension;
    public static Dimension battlefieldCardDimension;

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
        symbolTableSize = tableFontSize;
        flagHeight = tableFontSize - 2;
        balloonTooltipFont = new Font("Arial", 0, tableFontSize);
        if (tableFontSize > 15) {
            symbolEditorSize = tableFontSize - 5;
            dividerBarSize = 10 + (tableFontSize / 4);
            scrollBarSize = 14 + (tableFontSize / 4);
        } else {
            symbolEditorSize = tableFontSize;
            dividerBarSize = 10;
            scrollBarSize = 14;
        }

        // used for popup menus
        int dialogFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, 14);
        menuFont = new Font("Arial", 0, dialogFontSize);
        gameRequestsFont = new Font("Arial", 0, dialogFontSize);
        symbolDialogSize = dialogFontSize;

        int chatFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, 14);
        chatFont = new java.awt.Font("Arial", 0, chatFontSize);
        symbolChatSize = chatFontSize;

        int symbolSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_SYMBOL_SIZE, 14);
        // Set basic symbol size
        if (symbolSize < 25) {
            basicSymbolSize = "small";
        } else if (symbolSize < 45) {
            basicSymbolSize = "medium";
        } else {
            basicSymbolSize = "large";
        }
        if (symbolSize < 16) {
            symbolTooltipSize = 15;
            symbolPaySize = 15;
            symbolCardSize = 15;
        } else {
            symbolTooltipSize = symbolSize;
            symbolPaySize = symbolSize;
            symbolCardSize = symbolSize;
        }
        cardTooltipFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TOOLTIP_SIZE, 14);

        int handCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_HAND_SIZE, 14);
        handCardDimension = new Dimension(CARD_IMAGE_WIDTH * handCardSize / 42, CARD_IMAGE_HEIGHT * handCardSize / 42);

        int otherZonesCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_OTHER_ZONES_SIZE, 14);
        otherZonesCardDimension = new Dimension(CARD_IMAGE_WIDTH * otherZonesCardSize / 42, CARD_IMAGE_HEIGHT * otherZonesCardSize / 42);

        int battlefieldCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_BATTLEFIELD_SIZE, 14);
        battlefieldCardDimension = new Dimension(CARD_IMAGE_WIDTH * battlefieldCardSize / 42, CARD_IMAGE_HEIGHT * battlefieldCardSize / 42);
    }
}

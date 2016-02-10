/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.client.util;

import java.awt.Font;
import mage.client.MageFrame;
import mage.client.chat.ChatPanelBasic;
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
    public static int tableHeaderHeight = 24;
    public static int tableRowHeight = 20;

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
        setGUISize();
        for (ChatPanelBasic chatPanel : MageFrame.getChatPanels().values()) {
            chatPanel.changeGUISize(getChatFont());
        }
        MageFrame.getInstance().changeGUISize();
    }

    public static void setGUISize() {
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
            symbolPaySize = fontSize - 5;
            symbolCardSize = fontSize - 5;
        } else {
            symbolTooltipSize = fontSize;
            symbolPaySize = fontSize;
            symbolCardSize = fontSize;
        }

        tableRowHeight = fontSize + 4;
        tableHeaderHeight = fontSize + 10;
    }
}

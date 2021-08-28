package mage.client.util;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import org.mage.card.arcane.CardRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * @author LevelX2
 */
public final class GUISizeHelper {

    // relate the native image card size to a value of the size scale
    static final int CARD_IMAGE_WIDTH = 312;
    static final int CARD_IMAGE_HEIGHT = 445;
    static final int CARD_IMAG_VALUE = 42;

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

    public static int gameDialogAreaFontSizeBig = 16;
    public static int gameDialogAreaFontSizeTooltip = 14;
    public static int gameDialogAreaFontSizeSmall = 11;
    public static int gameDialogAreaTextHeight = 0;

    public static int gameDialogAreaButtonHigh = 16;

    public static Font gameDialogAreaFont = new java.awt.Font("Arial", 0, 12);
    public static int gameDialogButtonHeight;
    public static int gameDialogButtonWidth;

    public static Dimension handCardDimension;
    public static int stackWidth; // percent

    public static Dimension otherZonesCardDimension;
    public static int otherZonesCardVerticalOffset;

    public static Dimension battlefieldCardMinDimension;
    public static Dimension battlefieldCardMaxDimension;

    public static Dimension editorCardDimension;
    public static int editorCardVertOffsetInStack;
    public static int enlargedImageHeight;

    public static int getTableRowHeight() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        return fontSize + 6;
    }

    public static Font getTabFont() {
        int fontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TABLE_FONT_SIZE, 14);
        return new java.awt.Font("Arial", 0, fontSize);
    }

    public static Font getCardFont() {
        // default font type for some card panels (each render mode uses it's own font sizes)
        return new Font("Arial", Font.PLAIN, 14);
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

        // used in the feedback area of the game panel
        int feedbackFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_FEEDBACK_AREA_SIZE, 14);
        gameDialogAreaFontSizeBig = feedbackFontSize;
        gameDialogAreaFontSizeTooltip = feedbackFontSize - 2;
        gameDialogAreaFontSizeSmall = (feedbackFontSize / 2) + 2;
        gameDialogAreaTextHeight = GUISizeHelper.gameDialogAreaFontSizeBig + GUISizeHelper.gameDialogAreaFontSizeSmall + 30;

        gameDialogAreaButtonHigh = feedbackFontSize;
        gameDialogAreaFont = new Font("Arial", 0, feedbackFontSize);
        gameDialogButtonHeight = feedbackFontSize + 6;
        gameDialogButtonWidth = feedbackFontSize * 2 + 40;
        symbolDialogSize = feedbackFontSize;

        int chatFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, 14);
        chatFont = new java.awt.Font("Arial", 0, chatFontSize);
        symbolChatSize = chatFontSize;

        cardTooltipFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TOOLTIP_SIZE, 14);
        symbolTooltipSize = cardTooltipFontSize;

        int handCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_HAND_SIZE, 14);
        handCardDimension = new Dimension(CARD_IMAGE_WIDTH * handCardSize / 42, CARD_IMAGE_HEIGHT * handCardSize / 42);
        stackWidth = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_STACK_WIDTH, 30);

        int otherZonesCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_OTHER_ZONES_SIZE, 14);
        otherZonesCardDimension = new Dimension(CARD_IMAGE_WIDTH * otherZonesCardSize / 42, CARD_IMAGE_HEIGHT * otherZonesCardSize / 42);
        if (PreferencesDialog.getRenderMode() == 0) {
            otherZonesCardVerticalOffset = CardRenderer.getCardTopHeight(otherZonesCardDimension.width);
        } else if (otherZonesCardSize > 29) {
            otherZonesCardVerticalOffset = otherZonesCardDimension.height / 8;
        } else {
            otherZonesCardVerticalOffset = otherZonesCardDimension.height / 10;
        }

        int battlefieldCardMinSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_BATTLEFIELD_MIN_SIZE, 10);
        battlefieldCardMinDimension = new Dimension(CARD_IMAGE_WIDTH * battlefieldCardMinSize / 42, CARD_IMAGE_HEIGHT * battlefieldCardMinSize / 42);
        int battlefieldCardMaxSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_BATTLEFIELD_MAX_SIZE, 14);
        battlefieldCardMaxDimension = new Dimension(CARD_IMAGE_WIDTH * battlefieldCardMaxSize / 42, CARD_IMAGE_HEIGHT * battlefieldCardMaxSize / 42);

        int editorCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_EDITOR_SIZE, 14);
        editorCardDimension = new Dimension(CARD_IMAGE_WIDTH * editorCardSize / 42, CARD_IMAGE_HEIGHT * editorCardSize / 42);
        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_FALLBACK, "false").equals("false")) {
            editorCardVertOffsetInStack = CardRenderer.getCardTopHeight(editorCardDimension.width);
        } else {
            editorCardVertOffsetInStack = 2 * PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_OFFSET_SIZE, 14) - 10;
        }

        enlargedImageHeight = 25 * PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_ENLARGED_IMAGE_SIZE, 20);
    }

    public static int getTooltipCardWidth() {
        return 20 * GUISizeHelper.cardTooltipFontSize - 50;
    }

    public static int getTooltipCardHeight() {
        return 12 * GUISizeHelper.cardTooltipFontSize - 20;
    }

    public static void changePopupMenuFont(JPopupMenu popupMenu) {
        for (Component comp : popupMenu.getComponents()) {
            if (comp instanceof JMenuItem) {
                comp.setFont(GUISizeHelper.menuFont);
                if (comp instanceof JMenu) {
                    comp.setFont(GUISizeHelper.menuFont);
                    for (Component subComp : ((JMenu) comp).getMenuComponents()) {
                        subComp.setFont(GUISizeHelper.menuFont);
                    }
                }
            }
        }
    }

    public static String textToHtmlWithSize(String text, Font font) {
        if (text != null && !text.toLowerCase(Locale.ENGLISH).startsWith("<html>")) {
            return "<html><p style=\"font-size: " + font.getSize() + ";\">" + text + "</p>";
        }
        return text;
    }

    /**
     * Return scrollbar settings, so user can scroll it more faster for bigger cards
     *
     * @param cardSize card's wight or height (depends on vertical or horizontal scrollbar)
     * @return
     */
    public static int getCardsScrollbarUnitInc(int cardSize) {
        return Math.max(8, cardSize / 4);
    }
}

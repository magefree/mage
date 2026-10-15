package mage.client.util;

import mage.client.MageFrame;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.gui.GuiDisplayUtil;
import org.mage.card.arcane.CardRenderer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Helper class for GUI
 *
 * @author LevelX2, JayDi85
 */
public final class GUISizeHelper {

    // relate the native image card size to a value of the size scale
    static final int CARD_IMAGE_WIDTH = 312;
    static final int CARD_IMAGE_HEIGHT = 445;
    static final int CARD_IMAG_VALUE = 42;

    public static final float CARD_WIDTH_TO_HEIGHT_COEF = (1.0f * CARD_IMAGE_HEIGHT) / (1.0f * CARD_IMAGE_WIDTH);
    public static final float CARD_HEIGHT_TO_WIDTH_COEF = (1.0f * CARD_IMAGE_WIDTH) / (1.0f * CARD_IMAGE_HEIGHT);

    public static String basicSymbolSize = "small";
    static final int MIN_FONT_SIZE = 7;

    public static int symbolCardSize = 15;
    public static int symbolTableSize = 15;
    public static int symbolChatSize = 15;
    public static int symbolDialogSize = 15;
    public static int symbolTooltipSize = 15; // TODO: replace all symbolXXXSize by fontXXX usage
    public static int symbolPaySize = 15;
    public static int symbolEditorSize = 15;

    public static int tableHeaderHeight = 24;
    public static int tableRowHeight = 20;

    public static int dividerBarSize;
    public static int scrollBarSize;

    public static int tableFlagHeight;

    public static Font chatFont = new java.awt.Font("Arial", 0, 12);
    public static Font tableFont = new java.awt.Font("Arial", 0, 12);
    public static Font dialogFont = new java.awt.Font("Arial", 0, 12);
    public static Font cardTooltipFont = new java.awt.Font("Arial", 0, 12);

    public static int gameFeedbackPanelMainMessageFontSize = 16;
    public static int gameFeedbackPanelExtraMessageFontSize = 11;
    public static int gameFeedbackPanelMaxHeight = 0;

    public static int gamePhaseButtonSize = 36;
    public static int gameCommandButtonHeight = 32;

    public static Font gameFeedbackPanelFont = new java.awt.Font("Arial", 0, 12);
    public static int gameFeedbackPanelButtonHeight;
    public static int gameFeedbackPanelButtonWidth;

    public static Dimension handCardDimension;

    public static float playerPanelGuiScale;
    public static float dialogGuiScale;

    public static Dimension otherZonesCardDimension;
    public static int otherZonesCardVerticalOffset;

    public static Dimension battlefieldCardMinDimension;
    public static Dimension battlefieldCardMaxDimension;

    public static Dimension editorCardDimension;
    public static int editorCardVertOffsetInStack;
    public static int cardTooltipLargeImageHeight;
    public static int cardTooltipLargeTextHeight;
    public static int cardTooltipLargeTextWidth;

    public static Font getCardFont() {
        // default font type for some card panels (each render mode uses it's own font sizes)
        return new Font("Arial", Font.PLAIN, 14);
    }

    /**
     * Reset all caches and reload all GUI with actual settings.
     * Use it after GUI settings change like colors/fonts/sizes.
     *
     * @param reloadTheme use it after theme changes only
     */
    public static void refreshGUIAndCards(boolean reloadTheme) {
        calculateGUISizes();
        if (reloadTheme) {
            GuiDisplayUtil.refreshThemeSettings();
        }
        if (MageFrame.getInstance() != null) {
            MageFrame.getInstance().refreshGUIAndCards();
        }
    }

    public static void calculateGUISizes() {
        // app - dialogs and menus
        int dialogFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_DIALOG_FONT_SIZE, 14);
        dialogFont = new Font("Arial", 0, dialogFontSize);
        symbolDialogSize = dialogFontSize;
        // for auto-sizeable dialogs - use scale logic (example: player panel, pick choice, pick ability, etc)
        dialogGuiScale = dialogFontSize / 14.0f;

        // app - frame/window title
        // nimbus's LaF limited to static title size, so font can't be too big (related code in SynthInternalFrameTitlePane, BasicInternalFrameTitlePane)
        UIManager.put("InternalFrame.titleFont", new FontUIResource(dialogFont.deriveFont(Font.BOLD, Math.min(17, 0.8f * dialogFont.getSize()))));

        // app - tables
        tableFont = new java.awt.Font("Arial", 0, dialogFontSize);
        tableRowHeight = dialogFontSize + 4;
        tableHeaderHeight = dialogFontSize + 10;
        symbolTableSize = dialogFontSize;
        tableFlagHeight = dialogFontSize - 2;
        if (dialogFontSize > 15) {
            symbolEditorSize = dialogFontSize - 5;
            dividerBarSize = 10 + (dialogFontSize / 4);
            scrollBarSize = 14 + (dialogFontSize / 4);
        } else {
            symbolEditorSize = dialogFontSize;
            dividerBarSize = 10;
            scrollBarSize = 14;
        }

        // game - feedback panel
        gameFeedbackPanelFont = new Font("Arial", 0, dialogFontSize);
        gameFeedbackPanelMainMessageFontSize = dialogFontSize;
        gameFeedbackPanelExtraMessageFontSize = Math.max(MIN_FONT_SIZE, dialogFontSize / 2 + 2);
        gameFeedbackPanelMaxHeight = 20 + 2 * gameFeedbackPanelMainMessageFontSize + 2 * gameFeedbackPanelExtraMessageFontSize;
        gameFeedbackPanelButtonHeight = dialogFontSize + 6;
        gameFeedbackPanelButtonWidth = dialogFontSize * 2 + 40;

        // game - phase and command buttons
        gamePhaseButtonSize = dialogGuiScaleSize(36);
        gameCommandButtonHeight = dialogGuiScaleSize(32);

        // app - chats and game logs
        int chatFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CHAT_FONT_SIZE, 14);
        chatFont = new java.awt.Font("Arial", 0, chatFontSize);
        symbolChatSize = chatFontSize;

        // app - card popup and control's tooltip (e.g. mouse move over menu)
        int tooltipFontSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_TOOLTIP_SIZE, 17);
        cardTooltipFont = new java.awt.Font("Arial", 0, tooltipFontSize);
        symbolTooltipSize = tooltipFontSize;
        cardTooltipLargeImageHeight = 30 * tooltipFontSize;
        cardTooltipLargeTextWidth = Math.max(150, 20 * tooltipFontSize - 50);
        cardTooltipLargeTextHeight = Math.max(100, 12 * tooltipFontSize - 20);
        UIManager.put("ToolTip.font", new FontUIResource(cardTooltipFont));

        // app - information boxes (only title, text controls by content)
        // TODO: doesn't work
        //UIManager.put("OptionPane.titleFont", new FontUIResource(dialogFont.deriveFont(Font.BOLD, Math.min(17, 1.3f * dialogFont.getSize()))));

        // game - player panel
        playerPanelGuiScale = (float) (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_PLAYER_PANEL_SIZE, 14) / 14.0);

        // game - hand
        int handCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_HAND_SIZE, 14);
        handCardDimension = new Dimension(CARD_IMAGE_WIDTH * handCardSize / 42, CARD_IMAGE_HEIGHT * handCardSize / 42);

        int otherZonesCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_OTHER_ZONES_SIZE, 14);
        otherZonesCardDimension = new Dimension(CARD_IMAGE_WIDTH * otherZonesCardSize / 42, CARD_IMAGE_HEIGHT * otherZonesCardSize / 42);
        if (PreferencesDialog.getRenderMode() == 0) {
            otherZonesCardVerticalOffset = CardRenderer.getCardTopHeight(otherZonesCardDimension.width);
        } else if (otherZonesCardSize > 29) {
            otherZonesCardVerticalOffset = otherZonesCardDimension.height / 8;
        } else {
            otherZonesCardVerticalOffset = otherZonesCardDimension.height / 10;
        }

        int battlefieldCardAvgSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_BATTLEFIELD_SIZE, 14);
        int battlefieldMinSize = guiSizeScale(battlefieldCardAvgSize, 0.5f);
        int battlefieldMaxSize = guiSizeScale(battlefieldCardAvgSize, 1.5f);
        battlefieldCardMinDimension = new Dimension(CARD_IMAGE_WIDTH * battlefieldMinSize / 42, CARD_IMAGE_HEIGHT * battlefieldMinSize / 42);
        battlefieldCardMaxDimension = new Dimension(CARD_IMAGE_WIDTH * battlefieldMaxSize / 42, CARD_IMAGE_HEIGHT * battlefieldMaxSize / 42);

        // app - deck editor and draft
        int editorCardSize = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_GUI_CARD_EDITOR_SIZE, 14);
        editorCardDimension = new Dimension(CARD_IMAGE_WIDTH * editorCardSize / 42, CARD_IMAGE_HEIGHT * editorCardSize / 42);
        // make free space for card names in deck editor's stacks
        if (PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_IMAGE_MODE, "false").equals("false")) {
            // mtgo render
            editorCardVertOffsetInStack = CardRenderer.getCardTopHeight(editorCardDimension.width);
        } else {
            // image render
            editorCardVertOffsetInStack = Math.round(1.3f * GUISizeHelper.getImageRendererTitleFontSize(editorCardDimension.height));
        }
    }

    public static int getImageRendererMainFontSize(int cardHeight) {
        // startup font size (it same size on all zoom levels)
        return cardHeight / 13;
    }

    public static int getImageRendererTitleFontSize(int cardHeight) {
        // extracted from image renderer file cause title size used in deck editor
        return Math.max(13, getImageRendererMainFontSize(cardHeight));
    }

    public static void changePopupMenuFont(JPopupMenu popupMenu) {
        for (Component comp : popupMenu.getComponents()) {
            if (comp instanceof JMenuItem) {
                comp.setFont(GUISizeHelper.dialogFont);
                if (comp instanceof JMenu) {
                    comp.setFont(GUISizeHelper.dialogFont);
                    for (Component subComp : ((JMenu) comp).getMenuComponents()) {
                        subComp.setFont(GUISizeHelper.dialogFont);
                    }
                }
            }
        }
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

    /**
     * Scale GUI size values due scale coeff
     */
    public static int guiSizeScale(int value, float scaleMod) {
        // must keep 1 instead 0 on too small values
        if (value == 0) {
            return 0;
        } else if (value < 0) {
            return Math.min(-1, Math.round(value * scaleMod));
        } else {
            return Math.max(1, Math.round(value * scaleMod));
        }
    }

    /**
     * Scale GUI size values due scale coeff
     */
    public static float guiSizeScale(float value, float scaleMod) {
        return value * scaleMod;
    }

    public static int dialogGuiScaleSize(int value) {
        return guiSizeScale(value, dialogGuiScale);
    }

    public static Dimension dialogGuiScaleSize(Dimension dimension) {
        return new Dimension(dialogGuiScaleSize(dimension.width), dialogGuiScaleSize(dimension.height));
    }

    public static String textToHtmlWithSize(String text, Font font) {
        return textToHtmlWithSize(text, font.getSize());
    }

    public static String textToHtmlWithSize(String text, int fontSize) {
        if (text != null && !text.toLowerCase(Locale.ENGLISH).startsWith("<html>")) {
            return "<html><p style=\"font-size: " + fontSize + "pt;\">" + text + "</p>";
        }
        return text;
    }

    /**
     * Swing don't store name in the component, so it allow to find component by field's name instead
     */
    static public <T extends Component> T getComponentByFieldName(Window dialog, String name) {
        for (Field field : dialog.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (name.equals(field.getName())) {
                    final Object potentialMatch = field.get(dialog);
                    return (T) potentialMatch;
                }

            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ignore) {
            }
        }
        return null;
    }
}

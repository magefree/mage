package mage.client.themes;

import mage.abilities.hint.HintUtils;
import mage.abilities.icon.CardIconColor;
import org.mage.card.arcane.SvgUtils;
import org.mage.plugins.card.images.ImageCache;

import java.awt.*;

/**
 * @author 18ths, JayDi85
 */
public enum ThemeType {
    // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/_nimbusDefaults.html
    DEFAULT("Default",
            "",
            true,
            false,
            true,
            true,
            true,
            true,
            true,
            new Color(169, 176, 190), // nimbusBlueGrey
            new Color(214, 217, 223), // control
            new Color(255, 255, 255), // nimbusLightBackground
            new Color(242, 242, 189), // info
            new Color(51, 98, 140), // nimbusBase
            null, // mageToolbar
            new Color(200, 200, 180, 200), // playerPanel_inactiveBackgroundColor
            new Color(200, 255, 200, 200), // playerPanel_activeBackgroundColor
            new Color(131, 94, 83, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(169, 176, 190),
            Color.black,
            new Color(51, 98, 140)
    ),
    GREY("Grey",
            "grey-theme/",
            false,
            false,
            false,
            false,
            false,
            true,
            true,
            new Color(158, 158, 158), // nimbusBlueGrey
            new Color(212, 212, 212), // control
            new Color(215, 215, 215), // nimbusLightBackground
            new Color(189, 189, 164), // info
            new Color(102, 102, 102), // nimbusBase
            null, // mageToolbar
            new Color(172, 172, 172, 200), // playerPanel_inactiveBackgroundColor
            new Color(180, 234, 180, 200), // playerPanel_activeBackgroundColor
            new Color(99, 99, 99, 200), // playerPanel_deadBackgroundColor
            // card icons
            new Color(158, 158, 158),
            Color.black,
            Color.black
    ),
    SUNSET_VAPORWAVE("Vaporwave Sunset",
            "16bit-theme/",
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            new Color(246, 136, 158),
            new Color(243, 233, 164),
            new Color(204, 236, 201),
            new Color(117, 174, 238),
            new Color(106, 0, 255),
            new Color(192, 166, 232),
            new Color(243, 233, 164),
            new Color(204, 236, 201),
            new Color(106, 0, 255),
            // card icons
            new Color(246, 136, 158),
            Color.black,
            new Color(106, 0, 255)
    ),
    COFFEE("Coffee",
            "coffee-theme/",
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            new Color(219, 193, 172), // nimbusBlueGrey
            new Color(182, 157, 135), // control
            new Color(219, 193, 172), // nimbusLightBackground
            new Color(219, 197, 182), // info
            new Color(97, 27, 0), // nimbusBase
            new Color(219, 193, 172), // mageToolbar
            new Color(219, 193, 172),
            new Color(204, 236, 201),
            new Color(99, 72, 50, 255),
            // card icons
            new Color(219, 193, 172),
            Color.black,
            new Color(97, 27, 0)
    ),
    ISLAND("Island",
            "island-theme/",
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            new Color(172, 197, 219), // nimbusBlueGrey
            new Color(135, 158, 182), // control
            new Color(172, 197, 219), // nimbusLightBackground
            new Color(182, 200, 219), // info
            new Color(0, 78, 97), // nimbusBase
            new Color(172, 195, 219), // mageToolbar
            new Color(172, 195, 219),
            new Color(204, 236, 201),
            new Color(50, 68, 99, 255),
            // card icons
            new Color(172, 197, 219),
            Color.black,
            new Color(0, 78, 97)
    );

    private final String name;
    private final String path;
    private final boolean hasBackground;
    private final boolean hasLoginBackground;
    private final boolean hasBattleBackground;
    private final boolean hasSkipButtons;
    private final boolean hasPhaseIcons;
    private final boolean hasWinLossImages;
    private final boolean shortcutsVisibleForSkipButtons; // Whether or not to display skip button shortcuts
    private final Color nimbusBlueGrey;  // buttons, scrollbar background, disabled inputs
    private final Color control;  // window bg
    private final Color nimbusLightBackground; // inputs, table rows
    private final Color info;// tooltips
    private final Color nimbusBase;// title bars, scrollbar foreground
    private final Color mageToolbar;
    private final Color playerPanel_inactiveBackgroundColor;
    private final Color playerPanel_activeBackgroundColor;
    private final Color playerPanel_deadBackgroundColor;
    // card icons settings (example: flying icon)
    private final Color cardIconsFillColor;
    private final Color cardIconsStrokeColor;
    private final Color cardIconsTextColor;

    ThemeType(String name,
              String path,
              boolean hasBackground,
              boolean hasLoginBackground,
              boolean hasBattleBackground,
              boolean hasSkipButtons,
              boolean hasPhaseIcons,
              boolean hasWinLossImages,
              boolean shortcutsVisibleForSkipButtons,
              Color nimbusBlueGrey,
              Color control,
              Color nimbusLightBackground,
              Color info,
              Color nimbusBase,
              Color mageToolbar,
              Color playerPanel_inactiveBackgroundColor,
              Color playerPanel_activeBackgroundColor,
              Color playerPanel_deadBackgroundColor,
              Color cardIconsFillColor,
              Color cardIconsStrokeColor,
              Color cardIconsTextColor
    ) {
        this.name = name;
        this.path = path;
        this.hasBackground = hasBackground;
        this.hasLoginBackground = hasLoginBackground;
        this.hasBattleBackground = hasBattleBackground;
        this.hasSkipButtons = hasSkipButtons;
        this.hasPhaseIcons = hasPhaseIcons;
        this.hasWinLossImages = hasWinLossImages;
        this.shortcutsVisibleForSkipButtons = shortcutsVisibleForSkipButtons;
        this.nimbusBlueGrey = nimbusBlueGrey;
        this.control = control;
        this.nimbusLightBackground = nimbusLightBackground;
        this.info = info;
        this.nimbusBase = nimbusBase;
        this.mageToolbar = mageToolbar;
        this.playerPanel_activeBackgroundColor = playerPanel_activeBackgroundColor;
        this.playerPanel_deadBackgroundColor = playerPanel_deadBackgroundColor;
        this.playerPanel_inactiveBackgroundColor = playerPanel_inactiveBackgroundColor;
        this.cardIconsFillColor = cardIconsFillColor;
        this.cardIconsStrokeColor = cardIconsStrokeColor;
        this.cardIconsTextColor = cardIconsTextColor;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ThemeType valueByName(String value) {
        for (ThemeType themeType : values()) {
            if (themeType.name.equals(value)) {
                return themeType;
            }
        }
        return DEFAULT;
    }

    public String getName() {
        return name;
    }

    public boolean isShortcutsVisibleForSkipButtons() {
        return shortcutsVisibleForSkipButtons;
    }

    public Color getNimbusBlueGrey() {
        return nimbusBlueGrey;
    }

    public Color getControl() {
        return control;
    }

    public Color getNimbusLightBackground() {
        return nimbusLightBackground;
    }

    public Color getInfo() {
        return info;
    }

    public Color getNimbusBase() {
        return nimbusBase;
    }

    public Color getMageToolbar() {
        return mageToolbar;
    }

    public Color getPlayerPanel_inactiveBackgroundColor() {
        return playerPanel_inactiveBackgroundColor;
    }

    public Color getPlayerPanel_activeBackgroundColor() {
        return playerPanel_activeBackgroundColor;
    }

    public Color getPlayerPanel_deadBackgroundColor() {
        return playerPanel_deadBackgroundColor;
    }

    private String getImagePath(String imageType, String name) {
        return "/" + imageType + "/" + path + name;
    }

    public String getButtonPath(String name) {
        if (hasSkipButtons) {
            return getImagePath("buttons", name);
        } else {
            return "/buttons/" + name;
        }
    }

    public String getPhasePath(String name) {
        if (hasPhaseIcons) {
            return getImagePath("phases", name);
        } else {
            return "/phases/" + name;
        }
    }

    public String getWinlossPath(String name) {
        if (hasWinLossImages) {
            return getImagePath("winloss", name);
        } else {
            return "/winloss/" + name;
        }
    }

    public String getBackgroundPath() {
        if (hasBackground) {
            return getImagePath("background", "background.png");
        } else {
            return "/background/background.png";
        }
    }

    public String getLoginBackgroundPath() {
        if (hasLoginBackground) {
            return getImagePath("background", "login-background.png");
        } else {
            return getBackgroundPath();
        }
    }

    public String getBattleBackgroundPath() {
        if (hasBattleBackground) {
            return getImagePath("background", "battle-background.png");
        } else {
            return getBackgroundPath();
        }
    }

    public Color getCardIconsFillColor(CardIconColor cardIconColor) {
        return cardIconColor.getFillColor() != null ? cardIconColor.getFillColor() : this.cardIconsFillColor;
    }

    public Color getCardIconsStrokeColor(CardIconColor cardIconColor) {
        return cardIconColor.getStrokeColor() != null ? cardIconColor.getStrokeColor() : this.cardIconsStrokeColor;
    }

    public Color getCardIconsTextColor(CardIconColor cardIconColor) {
        return cardIconColor.getTextColor() != null ? cardIconColor.getTextColor() : this.cardIconsTextColor;
    }

    public String getCardIconsResourcePath(String resourceName) {
        return "/card/icons/" + resourceName;
    }

    public String getCardIconsCssFile(CardIconColor cardIconColor) {
        return String.format("card-icons-svg-settings-%s.css", cardIconColor.toString());
    }

    public String getCardIconsCssSettings(CardIconColor cardIconColor) {
        String fillColorVal = HintUtils.colorToHtml(this.getCardIconsFillColor(cardIconColor));
        String strokeColorVal = HintUtils.colorToHtml(this.getCardIconsStrokeColor(cardIconColor));

        return String.format(""
                        + "fill: %s;"
                        + "stroke: %s;"
                        + "stroke-width: 0.5;" // px
                        + "stroke-opacity: 0.7;", // 1 = 100%
                fillColorVal,
                strokeColorVal
        );
    }

    /**
     * Prepare theme settings and files before using. Call it on app loading or after theme changed
     */
    public void reload() {
        // reload card icons css file (run it all the time, even on svg unsupport mode)
        for (CardIconColor cardIconColor : CardIconColor.values()) {
            SvgUtils.prepareCss(this.getCardIconsCssFile(cardIconColor), this.getCardIconsCssSettings(cardIconColor), true);
        }

        // reload card icons and other rendering things from cache - it can depend on current theme
        ImageCache.clearCache();
    }
}
package mage.client.themes;

import mage.remote.Connection;

import java.awt.*;

public enum ThemeType {
    DEFAULT("Default Theme",
            "",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    ),
    SUNSET_VAPORWAVE("Sunset Vaporwave",
            "16bit-theme/",
            new Color(246, 136, 158),
            new Color(243, 233, 164),
            new Color(204, 236, 201),
            new Color(117, 174, 238),
            new Color(106, 0, 255),
            new Color(192, 166, 232),
            new Color(243, 233, 164),
            new Color(204, 236, 201),
            new Color(106, 0, 255)
    ),
    COFFEE("Coffee",
            "coffee-theme/",
            new Color(219, 193, 172), // nimbusBlueGrey
            new Color(182, 157, 135), // control
            new Color(219, 193, 172), // nimbusLightBackground
            new Color(219, 197, 182), // info
            new Color(97, 27, 0), // nimbusBase
            new Color(219, 193, 172), // mageToolbar
            new Color(219, 193, 172),
            new Color(204, 236, 201),
            new Color(99, 72, 50, 255)
    ),
    ISLAND("Island",
            "island-theme/",
            new Color(172, 197, 219), // nimbusBlueGrey
            new Color(135, 158, 182), // control
            new Color(172, 197, 219), // nimbusLightBackground
            new Color(182, 200, 219), // info
            new Color(0, 78, 97), // nimbusBase
            new Color(172, 195, 219), // mageToolbar
            new Color(172, 195, 219),
            new Color(204, 236, 201),
            new Color(50, 68, 99, 255)
    );

    private final String name;
    private final String path;
    private final Color nimbusBlueGrey;  // buttons, scrollbar background, disabled inputs
    private final Color control;  // window bg
    private final Color nimbusLightBackground; // inputs, table rows
    private final Color info;// tooltips
    private final Color nimbusBase;// title bars, scrollbar foreground
    private final Color mageToolbar;
    private final Color playerPanel_inactiveBackgroundColor;
    private final Color playerPanel_activeBackgroundColor;
    private final Color playerPanel_deadBackgroundColor;

    ThemeType(String name,
              String path,
              Color nimbusBlueGrey,
              Color control,
              Color nimbusLightBackground,
              Color info,
              Color nimbusBase,
              Color mageToolbar,
              Color playerPanel_inactiveBackgroundColor,
              Color playerPanel_activeBackgroundColor,
              Color playerPanel_deadBackgroundColor
    ) {
        this.name = name;
        this.path = path;
        this.nimbusBlueGrey = nimbusBlueGrey;
        this.control = control;
        this.nimbusLightBackground = nimbusLightBackground;
        this.info = info;
        this.nimbusBase = nimbusBase;
        this.mageToolbar = mageToolbar;
        this.playerPanel_activeBackgroundColor = playerPanel_activeBackgroundColor;
        this.playerPanel_deadBackgroundColor = playerPanel_activeBackgroundColor;
        this.playerPanel_inactiveBackgroundColor = playerPanel_inactiveBackgroundColor;
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

    public String getPath() {
        return this.path;
    }

    public String getName() {
        return name;
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
}

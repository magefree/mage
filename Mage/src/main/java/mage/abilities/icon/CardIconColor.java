package mage.abilities.icon;

import java.awt.*;

/**
 * For GUI: custom color modification for card icons (e.g. for special card icons like commander)
 * <p>
 * Card icons uses svg, so all color settings must be predefined on app's loading
 * <p>
 * TODO: current version uses panels (initCardIconsPanels) to define custom colors,
 * but it can be removed to single/dynamic icon in the future (example: r/g/b dynamic semaphore icon)
 *
 * @author JayDi85
 */
public enum CardIconColor {

    DEFAULT(),
    YELLOW(new Color(231, 203, 18), new Color(76, 76, 76), new Color(0, 0, 0)),
    RED(new Color(255, 31, 75), new Color(76, 76, 76), new Color(229, 228, 228)),
    GOLD(new Color(186, 105, 19), new Color(76, 76, 76), new Color(229, 228, 228));

    private final Color fillColor;
    private final Color strokeColor;
    private final Color textColor;

    CardIconColor() {
        this(null, null, null);
    }

    CardIconColor(Color fillColor, Color strokeColor, Color textColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.textColor = textColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public Color getTextColor() {
        return textColor;
    }
}

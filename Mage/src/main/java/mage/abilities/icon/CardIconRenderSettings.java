package mage.abilities.icon;

/**
 * Card icons drawing settings for MageCard
 *
 * @author JayDi85
 */
public class CardIconRenderSettings {

    // custom settings for test render dialog
    CardIconPosition customPosition = null;
    CardIconOrder customOrder = null;
    CardIconColor customColor = null;
    int customMaxVisibleCount = 0;
    int customIconSizePercent = 0;
    boolean debugMode = false;

    public CardIconRenderSettings() {
    }

    public CardIconRenderSettings withCustomPosition(CardIconPosition customPosition) {
        this.customPosition = customPosition;
        return this;
    }

    public CardIconRenderSettings withCustomOrder(CardIconOrder customOrder) {
        this.customOrder = customOrder;
        return this;
    }

    public CardIconRenderSettings withCustomColor(CardIconColor customColor) {
        this.customColor = customColor;
        return this;
    }

    public CardIconRenderSettings withCustomMaxVisibleCount(int customMaxVisibleCount) {
        this.customMaxVisibleCount = customMaxVisibleCount;
        return this;
    }

    public CardIconRenderSettings withCustomIconSizePercent(int customIconSizePercent) {
        this.customIconSizePercent = customIconSizePercent;
        return this;
    }

    public CardIconRenderSettings withDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return this;
    }

    public CardIconOrder getCustomOrder() {
        return customOrder;
    }

    public CardIconPosition getCustomPosition() {
        return customPosition;
    }

    public CardIconColor getCustomColor() {
        return customColor;
    }

    public int getCustomIconSizePercent() {
        return customIconSizePercent;
    }

    public int getCustomMaxVisibleCount() {
        return customMaxVisibleCount;
    }

    public boolean isDebugMode() {
        return debugMode;
    }
}

package mage.constants;

/**
 * @author North
 */
public enum SetType {
    EXPANSION("Expansion"),
    CORE("Core"),
    MAGIC_ONLINE("Magic Online"),
    MAGIC_ARENA("Magic Arena"),
    SUPPLEMENTAL("Supplemental"),
    SUPPLEMENTAL_STANDARD_LEGAL("Standard Legal Supplemental"),
    SUPPLEMENTAL_MODERN_LEGAL("Modern Legal Supplemental"),
    PROMOTIONAL("Promotional"),
    JOKESET("Joke Set"),
    CUSTOM_SET("Unofficial Set");

    private final String text;

    SetType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isCustomSet() {
        return this == SetType.CUSTOM_SET;
    }

    public boolean isJokeSet() {
        return this == SetType.JOKESET;
    }

    public boolean isEternalLegal() {
        // any official sets except un-sets
        return this != SetType.CUSTOM_SET && this != SetType.JOKESET && this != SetType.MAGIC_ARENA;
    }

    public boolean isStandardLegal() {
        // any official sets that was in standard
        return this == SetType.CORE || this == SetType.EXPANSION || this == SetType.SUPPLEMENTAL_STANDARD_LEGAL;
    }

    public boolean isModernLegal() {
        // any official sets that was in modern (standard + Modern Horizons)
        return this.isStandardLegal() || this == SetType.SUPPLEMENTAL_MODERN_LEGAL;
    }

    public boolean isHistoricLegal() {
        // any set made for standard or specifically for arena
        return this.isStandardLegal() || this == SetType.MAGIC_ARENA;
    }
}

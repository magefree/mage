package mage.constants;

public enum SubTypeSet {
    CreatureType,
    SpellType,
    BasicLandType(true),
    NonBasicLandType(true),
    EnchantmentType,
    ArtifactType,
    PlaneswalkerType;
    private final boolean isLand;

    SubTypeSet() {
        this(false);
    }

    SubTypeSet(boolean isLand) {
        this.isLand = isLand;
    }

    public boolean isLand() {
        return isLand;
    }
}

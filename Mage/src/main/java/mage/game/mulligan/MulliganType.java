package mage.game.mulligan;

public enum MulliganType {

    GAME_DEFAULT("Game Default"),
    VANCOUVER("Vancouver"),
    PARIS("Paris"),
    LONDON("London"),
    CANADIAN_HIGHLANDER("Canadian Highlander");

    private final String displayName;

    MulliganType(String displayName) {
        this.displayName = displayName;
    }

    public Mulligan getMulligan(int freeMulligans) {
        switch(this) {
            case PARIS:
                return new ParisMulligan(freeMulligans);
            case CANADIAN_HIGHLANDER:
                return new CanadianHighlanderMulligan(freeMulligans);
            case LONDON:
                return new LondonMulligan(freeMulligans);
            default:
            case VANCOUVER:
                return new VancouverMulligan(freeMulligans);
        }
    }

    @Override
    public String toString() {
        return displayName;
    }

    public MulliganType orDefault(MulliganType defaultMulligan) {
        if (this == GAME_DEFAULT) {
            return defaultMulligan;
        }
        return this;
    }

}

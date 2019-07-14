package mage.game.mulligan;

import java.util.Locale;

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
        switch (this) {
            case PARIS:
                return new ParisMulligan(freeMulligans);
            case CANADIAN_HIGHLANDER:
                return new CanadianHighlanderMulligan(freeMulligans);
            case VANCOUVER:
                return new VancouverMulligan(freeMulligans);
            default:
            case LONDON:
                return new LondonMulligan(freeMulligans);
        }
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static MulliganType valueByName(String name) {
        String search = (name != null ? name : "").toUpperCase(Locale.ENGLISH);

        MulliganType res = GAME_DEFAULT;
        for (MulliganType m : values()) {
            if (m.displayName.toUpperCase(Locale.ENGLISH).equals(search)) {
                res = m;
                break;
            }
        }
        return res;
    }

    public MulliganType orDefault(MulliganType defaultMulligan) {
        if (this == GAME_DEFAULT) {
            return defaultMulligan;
        }
        return this;
    }

}

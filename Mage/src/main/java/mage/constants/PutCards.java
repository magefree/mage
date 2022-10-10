package mage.constants;

/**
 *
 * @author awjackson
 */

public enum PutCards {
    HAND(Outcome.DrawCard, Zone.HAND, "into your hand"),
    GRAVEYARD(Outcome.Discard, Zone.GRAVEYARD, "into your graveyard"),
    BATTLEFIELD(Outcome.PutCardInPlay, Zone.BATTLEFIELD, "onto the battlefield"),
    BATTLEFIELD_TAPPED(Outcome.PutCardInPlay, Zone.BATTLEFIELD, "onto the battlefield tapped"),
    EXILED(Outcome.Exile, Zone.EXILED, "into exile"), // may need special case code to generate correct text
    TOP_OR_BOTTOM(Outcome.Benefit, Zone.LIBRARY, "on the top or bottom of your library"),
    TOP_ANY(Outcome.Benefit, Zone.LIBRARY, "on top of your library", " in any order"),
    BOTTOM_ANY(Outcome.Benefit, Zone.LIBRARY, "on the bottom of your library", " in any order"),
    BOTTOM_RANDOM(Outcome.Benefit, Zone.LIBRARY, "on the bottom of your library", " in a random order");

    private final Outcome outcome;
    private final Zone zone;
    private final String messageYour;
    private final String messageOwner;
    private final String order;

    PutCards(Outcome outcome, Zone zone, String message) {
        this(outcome, zone, message, "");
    }

    PutCards(Outcome outcome, Zone zone, String message, String order) {
        this.outcome = outcome;
        this.zone = zone;
        this.messageYour = message;
        this.messageOwner = message.replace("your", "its owner's");
        this.order = order;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Zone getZone() {
        return zone;
    }

    public String getMessage(boolean owner, boolean withOrder) {
        String message = owner ? messageOwner : messageYour;
        return withOrder ? message + order : message;
    }
}

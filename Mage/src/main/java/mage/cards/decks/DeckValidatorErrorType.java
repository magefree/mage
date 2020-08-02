package mage.cards.decks;

/**
 * @author JayDi85
 */
public enum DeckValidatorErrorType {

    PRIMARY(false, 10), // first errors to show (e.g. missing commander)
    DECK_SIZE(true, 20), // wrong deck size (deck must be legal while building)
    BANNED(false, 30),
    WRONG_SET(false, 40),
    OTHER(false, 50);

    private final boolean partlyLegal; // for deck legality panel: is it partly legal (e.g. show deck legal even without full deck size)
    private final int sortOrder; // errors list sort order from small to big

    DeckValidatorErrorType(boolean partlyLegal, int sortOrder) {
        this.partlyLegal = partlyLegal;
        this.sortOrder = sortOrder;
    }

    public boolean isPartlyLegal() {
        return this.partlyLegal;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }
}

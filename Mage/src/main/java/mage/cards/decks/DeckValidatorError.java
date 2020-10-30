package mage.cards.decks;

/**
 * @author JayDi85
 */
public class DeckValidatorError {

    private final DeckValidatorErrorType errorType;
    private final String group;
    private final String message;
    private final String cardName;

    public DeckValidatorError(DeckValidatorErrorType errorType, String group, String message, String cardName) {
        this.errorType = errorType;
        this.group = group;
        this.message = message;
        this.cardName = cardName;
    }

    public DeckValidatorErrorType getErrorType() {
        return this.errorType;
    }

    public String getGroup() {
        return this.group;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCardName() {
        return this.cardName;
    }
}

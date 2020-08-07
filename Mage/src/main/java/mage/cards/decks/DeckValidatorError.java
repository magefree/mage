package mage.cards.decks;

/**
 * @author JayDi85
 */
public class DeckValidatorError {

    private final DeckValidatorErrorType errorType;
    private final String group;
    private final String message;

    public DeckValidatorError(DeckValidatorErrorType errorType, String group, String message) {
        this.errorType = errorType;
        this.group = group;
        this.message = message;
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
}

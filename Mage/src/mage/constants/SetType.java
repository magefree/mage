package mage.constants;

/**
 *
 * @author North
 */
public enum SetType {
    CORE("Core"),
    DUEL_DECK("Duel Deck"),
    EXPANSION("Expansion"),
    NON_STANDARD_LEGAL_SETS("Non-standard-legal sets"),
    REPRINT("Reprint"),
    PROMOTIONAL("Promotional"),
    JOKESET("Joke Set");

    private final String text;

    SetType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

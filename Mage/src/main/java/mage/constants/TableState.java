package mage.constants;

/**
 *
 * @author North
 */
public enum TableState {
    WAITING ("Waiting for players"),
    READY_TO_START("Waiting to start"),
    STARTING ("Starting"),
    DRAFTING ("Drafting"),
    CONSTRUCTING ("Constructing"),
    DUELING ("Dueling"),
    SIDEBOARDING ("Sideboarding"),   
    FINISHED ("Finished");

    private final String text;

    TableState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}

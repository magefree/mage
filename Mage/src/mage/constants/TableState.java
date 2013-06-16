package mage.constants;

/**
 *
 * @author North
 */
public enum TableState {
    WAITING ("Waiting for players"),
    STARTING ("Waiting to start"),
    DRAFTING ("Drafting"),
    DUELING ("Dueling"),
    SIDEBOARDING ("Sideboarding"),
    CONSTRUCTING ("Constructing"),
    FINISHED ("Finished");

    private String text;

    TableState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}

package mage.constants;

/**
 *
 * @author LevelX2
 */

public enum TournamentPlayerState {
    JOINED ("Joined"),
    DRAFTING ("Drafting"),
    CONSTRUCTING ("Constructing"),
    DUELING ("Dueling"),
    SIDEBOARDING ("Sideboarding"),
    WAITING ("Waiting for next round"),
    ELIMINATED ("Eliminated"),    
    CANCELED ("Canceled"),
    FINISHED ("Finished"); // winner or player in swiss style

    private String text;

    TournamentPlayerState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}

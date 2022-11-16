package mage.remote;

/**
 *
 * @author LevelX2
 */
public enum DisconnectReason {
    LostConnection(" has lost connection"),
    BecameInactive(" has become inactive"),
    Disconnected(" has left XMage"),
    CleaningUp(" [cleaning up]"),
    ConnectingOtherInstance(" reconnected and replaced still active old session"),
    AdminDisconnect(" was disconnected by the Admin"),
    SessionExpired(" session expired"),
    ValidationError(" invalid"),
    Undefined("");

    String message;

    DisconnectReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

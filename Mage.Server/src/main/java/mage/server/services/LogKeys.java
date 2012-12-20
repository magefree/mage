package mage.server.services;

/**
 * @author noxx
 */
public interface LogKeys {

    String KEY_GAME_STARTED = "gameStarted";

    String KEY_USER_CONNECTED = "userConnected";

    String KEY_ADMIN_CONNECTED = "adminConnected";

    String KEY_SESSION_KILLED = "sessionKilled";

    String KEY_SESSION_DISCONNECTED = "sessionDisconnected";

    String KEY_SESSION_DISCONNECTED_BY_ADMIN = "sessionDisconnectedByAdmin";

    String KEY_NOT_VALID_SESSION = "sessionNotValid";

    String KEY_NOT_VALID_SESSION_INTERNAL = "sessionNotValidInternal";

    String KEY_TABLE_CREATED = "tableCreated";

    String KEY_TOURNAMENT_TABLE_CREATED = "tournamentTableCreated";

    String KEY_WRONG_VERSION = "wrongVersion";

    String KEY_NOT_ADMIN = "notAdminRestrictedOperation";

    String KEY_FEEDBACK_ADDED = "feedbackAdded";
}

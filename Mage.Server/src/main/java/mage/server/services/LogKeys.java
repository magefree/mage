package mage.server.services;

/**
 * @author noxx
 */
public interface LogKeys {

    public static final String KEY_GAME_STARTED = "gameStarted";

    public static final String KEY_USER_CONNECTED = "userConnected";

    public static final String KEY_ADMIN_CONNECTED = "adminConnected";

    public static final String KEY_SESSION_KILLED = "sessionKilled";

    public static final String KEY_SESSION_DISCONNECTED = "sessionDisconnected";

    public static final String KEY_SESSION_DISCONNECTED_BY_ADMIN = "sessionDisconnectedByAdmin";

    public static final String KEY_NOT_VALID_SESSION = "sessionNotValid";

    public static final String KEY_NOT_VALID_SESSION_INTERNAL = "sessionNotValidInternal";

    public static final String KEY_TABLE_CREATED = "tableCreated";

    public static final String KEY_TOURNAMENT_TABLE_CREATED = "tournamentTableCreated";

    public static final String KEY_WRONG_VERSION = "wrongVersion";

    public static final String KEY_NOT_ADMIN = "notAdminRestrictedOperation";
}
